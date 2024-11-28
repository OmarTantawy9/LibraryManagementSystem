package com.maids.librarymanagementsystem.controller;

import com.maids.librarymanagementsystem.configuration.AppConstants;
import com.maids.librarymanagementsystem.payload.PatronDTO;
import com.maids.librarymanagementsystem.payload.PatronResponse;
import com.maids.librarymanagementsystem.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    //  Retrieve a list of all patrons
    @GetMapping("/patrons")
    public ResponseEntity<PatronResponse> getAllPatrons(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PATRONS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder
    ){
        PatronResponse patronResponse = patronService.getAllPatrons(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<PatronResponse>(patronResponse, HttpStatus.OK);
    }

    // Retrieve details of a specific patron by ID
    @GetMapping("/patrons/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable("id") Long patronId){
        PatronDTO patronDTO = patronService.getPatronById(patronId);
        return new ResponseEntity<>(patronDTO, HttpStatus.FOUND);
    }

    // Add a new patron to the system
    @PostMapping("/patrons")
    public ResponseEntity<PatronDTO> addPatron(@Valid @RequestBody PatronDTO patronDTO){
        PatronDTO savedPatronDTO = patronService.addPatron(patronDTO);
        return new ResponseEntity<>(savedPatronDTO, HttpStatus.CREATED);
    }

    // Update an existing patron's information
    @PutMapping("/patrons/{id}")
    public ResponseEntity<PatronDTO> updatePatron(@Valid @RequestBody PatronDTO patronDTO,
                                                  @PathVariable("id") Long patronId){
        PatronDTO updatedPatronDTO = patronService.updatePatron(patronDTO, patronId);
        return new ResponseEntity<>(updatedPatronDTO, HttpStatus.OK);
    }

    // Remove a patron from the system
    @DeleteMapping("/patrons/{id}")
    public ResponseEntity<PatronDTO> deletePatron(@PathVariable("id") Long patronId){
        PatronDTO deletedPatronDTO = patronService.deletePatron(patronId);
        return new ResponseEntity<>(deletedPatronDTO, HttpStatus.OK);
    }

}
