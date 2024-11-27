package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.model.Patron;
import com.maids.librarymanagementsystem.payload.PatronDTO;
import com.maids.librarymanagementsystem.repository.PatronRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTests {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatronServiceImpl patronService;

    private Long patronId;
    private Patron patron;
    private PatronDTO patronDTO;

    @BeforeEach
    public void init(){
        patronId = 1L;
        patron = Patron.builder()
                .patronName("Patron Name")
                .patronPhone("+201151005010")
                .patronEmail("patron@example.com")
                .build();

        patronDTO = PatronDTO.builder()
                .patronName("Patron Name")
                .patronPhone("+201151005010")
                .patronEmail("patron@example.com")
                .build();
    }

    @Test
    public void PatronService_GetPatronById_ReturnsPatronDTO() {
        when(patronRepository.findById(patronId)).thenReturn(Optional.ofNullable(patron));
        when(modelMapper.map(any(Patron.class), eq(PatronDTO.class))).thenReturn(patronDTO);

        PatronDTO patronDTOFromService = patronService.getPatronById(patronId);

        Assertions.assertThat(patronDTOFromService).isNotNull();
    }


    @Test
    public void PatronService_AddPatron_ReturnsPatronDTO() {

        when(patronRepository.existsByPatronEmail(patronDTO.getPatronEmail())).thenReturn(false);
        when(patronRepository.existsByPatronPhone(patronDTO.getPatronPhone())).thenReturn(false);
        when(modelMapper.map(any(PatronDTO.class), eq(Patron.class))).thenReturn(patron);
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        when(modelMapper.map(any(Patron.class), eq(PatronDTO.class))).thenReturn(patronDTO);

        PatronDTO patronDTOFromService = patronService.addPatron(patronDTO);

        Assertions.assertThat(patronDTOFromService).isNotNull();

    }


    @Test
    public void PatronService_UpdatePatron_ReturnsPatronDTO() {

        when(patronRepository.findById(patronId)).thenReturn(Optional.ofNullable(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        when(modelMapper.map(any(Patron.class), eq(PatronDTO.class))).thenReturn(patronDTO);

        PatronDTO patronDTOFromService = patronService.updatePatron(patronDTO, patronId);

        Assertions.assertThat(patronDTOFromService).isNotNull();

    }

    @Test
    public void PatronService_DeletePatron_ReturnsPatronDTO() {

        when(patronRepository.findById(patronId)).thenReturn(Optional.ofNullable(patron));
        doNothing().when(patronRepository).delete(patron);
        when(modelMapper.map(any(Patron.class), eq(PatronDTO.class))).thenReturn(patronDTO);

        PatronDTO patronDTOFromService = patronService.deletePatron(patronId);

        Assertions.assertThat(patronDTOFromService).isNotNull();

    }

}
