package com.xbalek.tennis_court_reservation_system.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.dto.LoginRequest;
import com.xbalek.tennis_court_reservation_system.security.JWTProvider;
import com.xbalek.tennis_court_reservation_system.service.interfaces.CustomerServiceInterface;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceInterface customerService;

    @MockBean
    private JWTProvider tokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest("John Doe", "123456789");
        CustomerDTO customerDTO = new CustomerDTO(1L, "John Doe", "123456789", LocalDateTime.now());

        when(customerService.findByPhoneNumber("123456789")).thenReturn(customerDTO);
        when(tokenProvider.generateToken(customerDTO.getId())).thenReturn("mockToken");

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer mockToken"));
    }

    @Test
    void loginWithMissingFields() throws Exception {
        LoginRequest loginRequest = new LoginRequest(null, "123456789");

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithIncorrectName() throws Exception {
        LoginRequest loginRequest = new LoginRequest("Incorrect Name", "123456789");
        CustomerDTO customerDTO = new CustomerDTO(1L, "John Doe", "123456789", LocalDateTime.now());

        when(customerService.findByPhoneNumber(anyString())).thenReturn(customerDTO);

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginWithNonExistingUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("John Doe", "nonExisting");

        when(customerService.findByPhoneNumber(anyString())).thenReturn(null);

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void loginWithNullPhoneNumberShouldReturnBadRequest() throws Exception {
        LoginRequest loginRequest = new LoginRequest(null, "123456789");

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithNullNameShouldReturnBadRequest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("123456789", null);

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithNonExistentUserShouldReturnNotFound() throws Exception {
        LoginRequest loginRequest = new LoginRequest("123456789", "John Doe");

        when(customerService.findByPhoneNumber("123456789")).thenReturn(null);

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void loginWithInvalidNameShouldReturnUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest("123456789", "John Doe");
        CustomerDTO customerDTO = new CustomerDTO(1L, "Jane Doe", "123456789", LocalDateTime.now());

        when(customerService.findByPhoneNumber(anyString())).thenReturn(customerDTO);

        mockMvc.perform(post("/api/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}
