package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.configuration.AppConstants;
import com.maids.librarymanagementsystem.exception.APIException;
import com.maids.librarymanagementsystem.exception.ResourceNotFoundException;
import com.maids.librarymanagementsystem.model.Patron;
import com.maids.librarymanagementsystem.payload.PatronDTO;
import com.maids.librarymanagementsystem.payload.PatronResponse;
import com.maids.librarymanagementsystem.repository.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    private final ModelMapper modelMapper;

    @Override
    public PatronResponse getAllPatrons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageConfig = PageRequest.of(pageNumber, pageSize, sort);

        Page<Patron> patronsPage = patronRepository.findAll(pageConfig);
        List<Patron> patrons = patronsPage.getContent();

        if(patrons.isEmpty()){
            throw new APIException("No Patrons Found");
        }

        List<PatronDTO> patronDTOS = patrons.stream()
                .map(patron -> modelMapper.map(patron, PatronDTO.class))
                .toList();


        PatronResponse patronResponse = PatronResponse.builder()
                .content(patronDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(patronsPage.getTotalElements())
                .totalPages(patronsPage.getTotalPages())
                .lastPage(patronsPage.isLast())
                .build();

        return patronResponse;

    }

    @Override
    @Cacheable(value = AppConstants.PATRONS_CACHE, key = "#patronId")
    public PatronDTO getPatronById(Long patronId) {

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "patronId", patronId));

        return modelMapper.map(patron, PatronDTO.class);
    }

    @Transactional
    @Override
    @CachePut(value = AppConstants.PATRONS_CACHE, key = "#result.patronId")
    public PatronDTO addPatron(PatronDTO patronDTO) {

        if(patronRepository.existsByPatronEmail(patronDTO.getPatronEmail())){
            throw new APIException("Patron with the Provided Email Already Exists");
        }

        if(patronRepository.existsByPatronPhone(patronDTO.getPatronPhone())){
            throw new APIException("Patron with the Provided Phone Number Already Exists");
        }

        Patron patron = modelMapper.map(patronDTO, Patron.class);

        Patron savedPatron = patronRepository.save(patron);

        return modelMapper.map(savedPatron, PatronDTO.class);

    }

    @Transactional
    @Override
    @CachePut(value = AppConstants.PATRONS_CACHE, key = "#patronId")
    public PatronDTO updatePatron(PatronDTO patronDTO, Long patronId) {

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "patronId", patronId));

        patron.setPatronName(patronDTO.getPatronName());
        patron.setPatronPhone(patronDTO.getPatronPhone());
        patron.setPatronEmail(patronDTO.getPatronEmail());

        Patron savedPatron = patronRepository.save(patron);

        return modelMapper.map(savedPatron, PatronDTO.class);

    }

    @Transactional
    @Override
    @CacheEvict(value = AppConstants.PATRONS_CACHE, key = "#patronId")
    public PatronDTO deletePatron(Long patronId) {

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "patronId", patronId));

        patronRepository.delete(patron);

        return modelMapper.map(patron, PatronDTO.class);

    }

}
