package com.maids.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.librarymanagementsystem.model.Book;
import com.maids.librarymanagementsystem.model.Patron;
import com.maids.librarymanagementsystem.payload.BorrowingRecordDTO;
import com.maids.librarymanagementsystem.security.jwt.JWTUtils;
import com.maids.librarymanagementsystem.service.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = BorrowingRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BorrowingRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BorrowingRecordServiceImpl borrowingService;

    private Book book;
    private Patron patron;
    private Long bookId;
    private Long patronId;
    private BorrowingRecordDTO borrowingRecordDTO_NotReturned;
    private BorrowingRecordDTO borrowingRecordDTO_Returned;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

    @BeforeEach
    public void init(){
        bookId = 1L;
        patronId = 1L;
        borrowingDate = LocalDate.now();
        returnDate = LocalDate.now();

        book = Book.builder()
                .title("Title")
                .author("AuthorName")
                .isbn("978-1503278196")
                .build();

        patron = Patron.builder()
                .patronName("Patron Name")
                .patronEmail("patron@example.com")
                .patronPhone("+1 843-749-3820")
                .build();

        borrowingRecordDTO_NotReturned = BorrowingRecordDTO.builder()
                .borrowingDate(borrowingDate)
                .book(book)
                .patron(patron)
                .build();

        borrowingRecordDTO_Returned = BorrowingRecordDTO.builder()
                .borrowingDate(borrowingDate)
                .returnDate(borrowingDate)
                .book(book)
                .patron(patron)
                .build();

    }

    @Test
    public void BorrowingRecordController_BorrowBook_BorrowingRecordDTO() throws Exception {

        when(borrowingService.borrowBook(bookId, patronId)).thenReturn(borrowingRecordDTO_NotReturned);

        ResultActions resultActions = mockMvc.perform(post("/api/borrow/" + bookId + "/patron/" + patronId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowingDate").value(borrowingDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.book").value(book))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patron").value(patron));

    }

    @Test
    public void BorrowingRecordController_ReturnBook_BorrowingRecordDTO() throws Exception {

        when(borrowingService.returnBook(bookId, patronId)).thenReturn(borrowingRecordDTO_Returned);

        ResultActions resultActions = mockMvc.perform(put("/api/return/" + bookId + "/patron/" + patronId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.borrowingDate").value(borrowingDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate").value(returnDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book").value(book))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patron").value(patron));

    }

}
