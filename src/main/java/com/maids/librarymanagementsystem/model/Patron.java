package com.maids.librarymanagementsystem.model;

import com.maids.librarymanagementsystem.configuration.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patrons",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "patron_phone"),
                @UniqueConstraint(columnNames = "patron_email")
        })
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patronId;

    @NotBlank(message = "Name Must be Provided")
    @Size(min = 3, max = 50, message = "Patron Name Must at least be 3 letters long and at most 50 letters long")
    private String patronName;

    @NotBlank(message = "Phone Number Must be Provided")
    @Pattern(regexp = AppConstants.PHONE_NUMBER_REGEX, message = "Phone Number Must be Valid")
    private String patronPhone;

    @NotBlank(message = "Email Must be Provided")
    @Email(message = "Email Must be Valid")
    private String patronEmail;

}

