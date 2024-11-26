package com.maids.librarymanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatronDTO {
    private Long patronId;

    private String patronName;

    private String patronPhone;

    private String patronEmail;
}
