package com.maids.librarymanagementsystem.security.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "First Name Must be Provided")
    @Size(min = 3, max = 50, message = "First Name Must at least be 3 letters long and at most 50 letters long")
    private String firstName;

    @NotBlank(message = "Last Name Must be Provided")
    @Size(min = 3, max = 50, message = "Last Name Must at least be 3 letters long and at most 50 letters long")
    private String lastName;

    @NotBlank(message = "Username Must be Provided")
    @Size(min = 3, max = 30, message = "Username Must at least be 6 letters long and at most 30 letters long")
    private String username;

    @NotBlank(message = "Password Must be Provided")
    @Size(min = 3, max = 20, message = "Password Must at least be 3 letters long and at most 20 letters long")
    private String password;

    @NotBlank
    private String role;

}
