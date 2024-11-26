package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.payload.BookDTO;
import com.maids.librarymanagementsystem.payload.BookResponse;

public interface BookService {
    BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BookDTO getBookById(Long bookId);

    BookDTO addBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO, Long bookId);

    BookDTO deleteBook(Long bookId);
}
