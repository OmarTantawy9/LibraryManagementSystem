package com.maids.librarymanagementsystem.exception;

import com.maids.librarymanagementsystem.payload.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), false);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ExceptionResponse> APIExceptionHandler(APIException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), false);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> ConstraintViolationExceptionHandler(ConstraintViolationException e){

        String violationMessage = e.getConstraintViolations().iterator().next().getMessage();

        ExceptionResponse exceptionResponse = new ExceptionResponse(violationMessage, false);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> DataIntegrityViolationExceptionHandler(DataIntegrityViolationException e){

        String violationMessage = "Data Integrity Violation";

        if(e.getMessage().contains("Unique index or primary key violation")){
            violationMessage = "Unique index or primary key violation";
        }


        ExceptionResponse exceptionResponse = new ExceptionResponse(violationMessage, false);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}