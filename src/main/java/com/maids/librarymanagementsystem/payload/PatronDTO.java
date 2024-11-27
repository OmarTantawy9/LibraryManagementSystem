package com.maids.librarymanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatronDTO {
    private Long patronId;

    private String patronName;

    private String patronPhone;

    private String patronEmail;
}
