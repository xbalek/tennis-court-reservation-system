package com.xbalek.tennis_court_reservation_system.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbalek.tennis_court_reservation_system.configuration.SecurityConfig;
import com.xbalek.tennis_court_reservation_system.dto.TennisCourtDTO;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.TennisCourtService;

@WebMvcTest(TennisCourtController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class TennisCourtControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TennisCourtService tennisCourtService;

    @MockBean
    private JWTHeaderValidator jwtHeaderValidator;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_Found() throws Exception {
        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO(1L, "Court 1", null);
        when(tennisCourtService.findById(1L)).thenReturn(tennisCourtDTO);

        mockMvc.perform(get("/api/tennis-courts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tennisCourtDTO)));
    }

    @Test
    void findById_NotFound() throws Exception {
        when(tennisCourtService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/tennis-courts/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_Found() throws Exception {
        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO(1L, "Court 1", null);
        when(tennisCourtService.getAll()).thenReturn(new TennisCourtDTO[] { tennisCourtDTO });

        mockMvc.perform(get("/api/tennis-courts/"))
                .andExpect(status().isOk());
    }
}

