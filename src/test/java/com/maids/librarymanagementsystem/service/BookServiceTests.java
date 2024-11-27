package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.payload.BookDTO;
import com.maids.librarymanagementsystem.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Long bookId;
    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    public void init(){
        bookId = 1L;

        book = Book.builder()
                .title("Book Title")
                .author("Author")
                .publicationYear(2024)
                .isbn("978-1503278196")
                .build();

        bookDTO = BookDTO.builder()
                .title("Book Title")
                .author("Author")
                .publicationYear(2024)
                .isbn("978-1503278196")
                .build();
    }

    @Test
    public void BookService_GetBookById_ReturnsBookDTO(){

        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);

        BookDTO bookDTOFromService = bookService.getBookById(bookId);

        Assertions.assertThat(bookDTOFromService).isNotNull();

    }

    @Test
    public void BookService_AddBook_ReturnsBookDTO(){

        when(bookRepository.existsByIsbn(bookDTO.getIsbn())).thenReturn(false);
        when(modelMapper.map(any(BookDTO.class), eq(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO bookDTOFromService = bookService.addBook(bookDTO);

        Assertions.assertThat(bookDTOFromService).isNotNull();

    }

    @Test
    public void BookService_UpdateBook_ReturnsBookDTO(){

        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);

        BookDTO bookDTOFromService = bookService.updateBook(bookDTO, bookId);

        Assertions.assertThat(bookDTOFromService).isNotNull();

    }

    @Test
    public void BookService_DeleteBook_ReturnsBookDTO(){

        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        doNothing().when(bookRepository).delete(book);
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTO);

        BookDTO bookDTOFromService = bookService.deleteBook(bookId);

        Assertions.assertThat(bookDTOFromService).isNotNull();

    }

}
