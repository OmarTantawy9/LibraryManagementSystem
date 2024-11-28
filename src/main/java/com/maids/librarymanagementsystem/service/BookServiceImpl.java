package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.configuration.AppConstants;
import com.maids.librarymanagementsystem.exception.APIException;
import com.maids.librarymanagementsystem.exception.ResourceNotFoundException;
import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.payload.BookDTO;
import com.maids.librarymanagementsystem.payload.BookResponse;
import com.maids.librarymanagementsystem.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Override
    public BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortConfig = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageConfig = PageRequest.of(pageNumber, pageSize, sortConfig);

        Page<Book> bookPage = bookRepository.findAll(pageConfig);
        List<Book> books = bookPage.getContent();

        if(books.isEmpty()){
            throw new APIException("No Books Found");
        }

        List<BookDTO> bookDTOS = books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();


        BookResponse bookResponse = BookResponse.builder()
                .content(bookDTOS)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .lastPage(bookPage.isLast())
                .build();

        return bookResponse;
    }

    @Override
    @Cacheable(value = AppConstants.BOOKS_CACHE, key = "#bookId")
    public BookDTO getBookById(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));

        return modelMapper.map(book, BookDTO.class);
    }

    @Transactional
    @Override
    @CachePut(value = AppConstants.BOOKS_CACHE, key = "#result.bookId")
    public BookDTO addBook(BookDTO bookDTO) {

        // Check if the book already exists
        if(bookRepository.existsByIsbn(bookDTO.getIsbn())){
            throw new APIException("Book already exists");
        }

        Book book = modelMapper.map(bookDTO, Book.class);

        book = bookRepository.save(book);

        return modelMapper.map(book, BookDTO.class);
    }

    @Transactional
    @Override
    @CachePut(value = AppConstants.BOOKS_CACHE, key = "#bookId")
    public BookDTO updateBook(BookDTO bookDTO, Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));


        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setIsbn(bookDTO.getIsbn());

        Book updatedBook = bookRepository.save(book);

        return modelMapper.map(updatedBook, BookDTO.class);
    }


    @Transactional
    @Override
    @CacheEvict(value = AppConstants.BOOKS_CACHE, key = "#bookId")
    public BookDTO deleteBook(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));

        bookRepository.delete(book);

        return modelMapper.map(book, BookDTO.class);

    }

}
