package com.maids.librarymanagementsystem.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("No %s found with %s: %s", resourceName, field, fieldId));
    }


}
