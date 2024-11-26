package com.maids.librarymanagementsystem.controller;

import com.maids.librarymanagementsystem.configuration.AppConstants;
import com.maids.librarymanagementsystem.payload.BookDTO;
import com.maids.librarymanagementsystem.payload.BookResponse;
import com.maids.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Retrieve a list of all books
    @GetMapping("/books")
    public ResponseEntity<BookResponse> getAllBooks(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOKS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder
    ){
        BookResponse bookResponse = bookService.getAllBooks(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    // Retrieve details of a specific book by ID
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long bookId){
        BookDTO bookDTO = bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDTO, HttpStatus.FOUND);
    }

    //Add a new book to the library
    @PostMapping("/books")
    private ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO){
        BookDTO savedBookDTO = bookService.addBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    // Update an existing book's information
    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO,
                                              @PathVariable("id") Long bookId){
        BookDTO updatedBookDTO = bookService.updateBook(bookDTO, bookId);
        return new ResponseEntity<>(updatedBookDTO, HttpStatus.OK);
    }

    // Remove a book from the library
    @DeleteMapping("/books/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable("id") Long bookId){
        BookDTO deletedBookDTO = bookService.deleteBook(bookId);
        return new ResponseEntity<>(deletedBookDTO, HttpStatus.OK);
    }

}
