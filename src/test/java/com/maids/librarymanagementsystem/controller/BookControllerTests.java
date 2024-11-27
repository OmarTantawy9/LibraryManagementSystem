package com.maids.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.librarymanagementsystem.payload.BookDTO;
import com.maids.librarymanagementsystem.payload.BookResponse;
import com.maids.librarymanagementsystem.security.jwt.JWTUtils;
import com.maids.librarymanagementsystem.service.BookServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookServiceImpl bookService;

    private Long bookId;
    private BookDTO bookDTO;

    @BeforeEach
    public void init(){
        bookId = 1L;

        bookDTO = BookDTO.builder()
                .title("Book Title")
                .author("Author")
                .isbn("978-1503278196")
                .build();
    }


    @Test
    public void BookController_GetAllBooks_ReturnsBookResponse() throws Exception {

        Integer pageNumber = 1;
        Integer pageSize = 10;
        Long totalElements = 10L;
        Integer totalPages = 1;
        boolean lastPage = true;


        BookResponse bookResponse = BookResponse.builder()
                .content(Arrays.asList(bookDTO))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .lastPage(lastPage)
                .build();

        when(bookService.getAllBooks(pageNumber, pageSize, "bookId", "asc")).thenReturn(bookResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize))
                .param("sortBy", "bookId")
                .param("sortOrder", "asc")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(bookResponse.getContent().size())));

    }


    @Test
    public void BookController_GetBookById_ReturnsBookResponse() throws Exception {

        when(bookService.getBookById(bookId)).thenReturn(bookDTO);


        ResultActions resultActions = mockMvc.perform(get("/api/books/" + String.valueOf(bookId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1503278196"));;

    }


    @Test
    public void BookController_AddBook_ReturnsCreated() throws Exception {
        BDDMockito.given(bookService.addBook(any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1503278196"));

    }

    @Test
    public void BookController_UpdateBook_ReturnsBookResponse() throws Exception {

        when(bookService.updateBook(bookDTO, bookId)).thenReturn(bookDTO);


        ResultActions resultActions = mockMvc.perform(put("/api/books/" + String.valueOf(bookId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1503278196"));;

    }

    @Test
    public void BookController_DeleteBook_ReturnsBookResponse() throws Exception {

        when(bookService.deleteBook(bookId)).thenReturn(bookDTO);


        ResultActions resultActions = mockMvc.perform(delete("/api/books/" + String.valueOf(bookId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-1503278196"));;

    }

}
