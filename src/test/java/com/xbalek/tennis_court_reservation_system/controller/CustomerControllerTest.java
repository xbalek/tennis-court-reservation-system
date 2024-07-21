package com.xbalek.tennis_court_reservation_system.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.interfaces.CustomerServiceInterface;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceInterface customerService;

    @MockBean
    private JWTHeaderValidator jwtHeaderValidator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdSuccess() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1L, "John Doe", "123456789", LocalDateTime.now());
        when(customerService.findById(1L)).thenReturn(customerDTO);
        when(jwtHeaderValidator.isAdminFromRequest(any())).thenReturn(true);

        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdUnauthorized() throws Exception {
        when(jwtHeaderValidator.isAdminFromRequest(any())).thenReturn(false);
        when(jwtHeaderValidator.validateRequest(any(), eq(1L))).thenReturn(false);

        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findByPhoneNumberSuccess() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1L, "John Doe", "123456789", LocalDateTime.now());
        when(customerService.findByPhoneNumber("123456789")).thenReturn(customerDTO);
        when(jwtHeaderValidator.isAdminFromRequest(any())).thenReturn(true);

        mockMvc.perform(get("/api/customer/phone/123456789"))
                .andExpect(status().isOk());
    }

    @Test
    void getReservationSuccess() throws Exception {
        ReservationDTO[] reservations = { new ReservationDTO(null, null, null, null, null, null, null) };
        when(customerService.getReservation(anyString(), anyBoolean())).thenReturn(reservations);
        when(jwtHeaderValidator.isAdminFromRequest(any())).thenReturn(true);

        mockMvc.perform(get("/api/customer/123456789/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reservations)));
    }

}
