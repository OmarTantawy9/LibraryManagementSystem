package com.maids.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.librarymanagementsystem.payload.PatronDTO;
import com.maids.librarymanagementsystem.payload.PatronResponse;
import com.maids.librarymanagementsystem.security.jwt.JWTUtils;
import com.maids.librarymanagementsystem.service.PatronServiceImpl;
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

@WebMvcTest(controllers = PatronController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PatronControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatronServiceImpl patronService;

    private Long patronId;
    private PatronDTO patronDTO;

    @BeforeEach
    public void init(){
        patronId = 1L;

        patronDTO = PatronDTO.builder()
                .patronName("Patron Name")
                .patronPhone("+201151005010")
                .patronEmail("patron@example.com")
                .build();
    }


    @Test
    public void PatronController_GetAllPatrons_ReturnsPatronResponse() throws Exception {

        Integer pageNumber = 1;
        Integer pageSize = 10;
        Long totalElements = 10L;
        Integer totalPages = 1;
        boolean lastPage = true;


        PatronResponse patronResponse = PatronResponse.builder()
                .content(Arrays.asList(patronDTO))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .lastPage(lastPage)
                .build();

        when(patronService.getAllPatrons(pageNumber, pageSize, "patronId", "asc")).thenReturn(patronResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize))
                .param("sortBy", "patronId")
                .param("sortOrder", "asc")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(patronResponse.getContent().size())));

    }


    @Test
    public void PatronController_GetPatronById_ReturnsPatronResponse() throws Exception {

        when(patronService.getPatronById(patronId)).thenReturn(patronDTO);


        ResultActions resultActions = mockMvc.perform(get("/api/patrons/" + patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronName").value("Patron Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronPhone").value("+201151005010"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronEmail").value("patron@example.com"));
    }


    @Test
    public void PatronController_AddPatron_ReturnsCreated() throws Exception {
        BDDMockito.given(patronService.addPatron(any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(post("/api/patrons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronName").value("Patron Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronPhone").value("+201151005010"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronEmail").value("patron@example.com"));

    }

    @Test
    public void PatronController_UpdatePatron_ReturnsPatronResponse() throws Exception {

        when(patronService.updatePatron(patronDTO, patronId)).thenReturn(patronDTO);


        ResultActions resultActions = mockMvc.perform(put("/api/patrons/" + patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronName").value("Patron Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronPhone").value("+201151005010"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronEmail").value("patron@example.com"));

    }

    @Test
    public void PatronController_DeletePatron_ReturnsPatronResponse() throws Exception {

        when(patronService.deletePatron(patronId)).thenReturn(patronDTO);


        ResultActions resultActions = mockMvc.perform(delete("/api/patrons/" + patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patronDTO))
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronName").value("Patron Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronPhone").value("+201151005010"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patronEmail").value("patron@example.com"));

    }

}
