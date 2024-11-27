package com.maids.librarymanagementsystem.service;

import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.BorrowingRecord;
import com.maids.librarymanagementsystem.model.Patron;
import com.maids.librarymanagementsystem.payload.BorrowingRecordDTO;
import com.maids.librarymanagementsystem.repository.BookRepository;
import com.maids.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.maids.librarymanagementsystem.repository.PatronRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTests {

    @Mock
    private BorrowingRecordRepository borrowingRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private Long bookId;
    private Book book;

    private Long patronId;
    private Patron patron;

    private BorrowingRecord borrowingRecord_NotReturned;
    private BorrowingRecordDTO borrowingRecordDTO_NotReturned;

    private BorrowingRecord borrowingRecord_Returned;
    private BorrowingRecordDTO borrowingRecordDTO_Returned;


    @BeforeEach
    public void init(){
        bookId = 1L;

        book = Book.builder()
                .title("Book Title")
                .author("Author")
                .publicationYear(2024)
                .isbn("978-1503278196")
                .build();

        patronId = 1L;
        patron = Patron.builder()
                .patronName("Patron Name")
                .patronPhone("+201151005010")
                .patronEmail("patron@example.com")
                .build();

        borrowingRecord_NotReturned = BorrowingRecord.builder()
                .borrowingDate(LocalDate.now())
                .book(book)
                .patron(patron)
                .build();

        borrowingRecordDTO_NotReturned = BorrowingRecordDTO.builder()
                .borrowingDate(LocalDate.now())
                .book(book)
                .patron(patron)
                .build();

        borrowingRecord_Returned = BorrowingRecord.builder()
                .borrowingDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .book(book)
                .patron(patron)
                .build();

        borrowingRecordDTO_Returned = BorrowingRecordDTO.builder()
                .borrowingDate(LocalDate.now())
                .returnDate(LocalDate.now())
                .book(book)
                .patron(patron)
                .build();

    }


    @Test
    public void BorrowingRecordService_BorrowBook_ReturnsBorrowingRecordDTO() {

        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.ofNullable(patron));
        when(borrowingRepository.save(borrowingRecord_NotReturned)).thenReturn(borrowingRecord_NotReturned);
        when(modelMapper.map(borrowingRecord_NotReturned, BorrowingRecordDTO.class)).thenReturn(borrowingRecordDTO_NotReturned);

        BorrowingRecordDTO borrowingDTOFromService = borrowingRecordService.borrowBook(bookId, patronId);

        Assertions.assertThat(borrowingDTOFromService).isNotNull();

    }


    @Test
    public void BorrowingRecordService_ReturnBook_ReturnsBorrowingRecordDTO() {

        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.ofNullable(patron));
        when(borrowingRepository.findAllByBookAndPatronOrderByBorrowingRecordIdDesc(book, patron)).thenReturn(List.of(borrowingRecord_NotReturned));
        when(borrowingRepository.save(borrowingRecord_Returned)).thenReturn(borrowingRecord_Returned);
        when(modelMapper.map(borrowingRecord_Returned, BorrowingRecordDTO.class)).thenReturn(borrowingRecordDTO_Returned);

        BorrowingRecordDTO borrowingDTOFromService = borrowingRecordService.returnBook(bookId, patronId);

        Assertions.assertThat(borrowingDTOFromService).isNotNull();

    }


}
