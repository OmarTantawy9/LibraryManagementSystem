package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.payload.PatronDTO;
import com.maids.librarymanagementsystem.payload.PatronResponse;

public interface PatronService {
    PatronResponse getAllPatrons(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    PatronDTO getPatronById(Long patronId);

    PatronDTO addPatron(PatronDTO patronDTO);

    PatronDTO updatePatron(PatronDTO patronDTO, Long patronId);

    PatronDTO deletePatron(Long patronId);
}
