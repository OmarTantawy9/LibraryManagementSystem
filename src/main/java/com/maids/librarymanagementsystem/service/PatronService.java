package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.payload.PatronDTO;
import com.maids.librarymanagementsystem.payload.PatronResponse;
import jakarta.validation.Valid;

public interface PatronService {
    PatronResponse getAllPatrons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PatronDTO getPatronById(Long patronId);

    PatronDTO addPatron(@Valid PatronDTO patronDTO);

    PatronDTO updatePatron(@Valid PatronDTO patronDTO, Long patronId);

    PatronDTO deletePatron(Long patronId);
}
