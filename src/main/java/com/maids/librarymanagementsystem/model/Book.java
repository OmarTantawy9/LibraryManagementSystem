package com.maids.librarymanagementsystem.model;

import com.maids.librarymanagementsystem.configuration.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "books",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "isbn")
        })
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotBlank(message = "Book Title must be provided")
    @Size(min = 2, max = 30, message = "size must be between 2 and 30")
    private String title;

    @NotBlank(message = "Author must be provided")
    @Size(min = 2, max = 30, message = "Author Name must be between 2 and 30")
    private String author;

    @NotNull(message = "Publication Year must be provided")
    @Min(1800)
    @Max(9999)
    private Integer publicationYear;

    @NotBlank(message = "ISBN must be provided")
    @Pattern(regexp = AppConstants.BOOK_VALID_ISBN_REGEX, message = "ISBN must be valid")
    private String isbn;
}
