package com.maids.librarymanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long bookId;

    private String title;

    private String author;

    private Integer publicationYear;

    private String isbn;
}
