package com.xbalek.tennis_court_reservation_system.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.TennisCourt;
import com.xbalek.tennis_court_reservation_system.security.JWTHeaderValidator;
import com.xbalek.tennis_court_reservation_system.service.interfaces.ReservationServiceInterface;

import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReservationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationServiceInterface reservationService;

    @MockBean
    private JWTHeaderValidator jwtHeaderValidator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "/api/reservation/";

    private ReservationDTO mockReservationDTO;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        MockitoAnnotations.openMocks(this);

        Customer mockCustomer = new Customer();
        TennisCourt mockTennisCourt = new TennisCourt();
        mockReservationDTO = new ReservationDTO(1L, mockTennisCourt, mockCustomer, null, null, false, null);
    }

    @Test
    void testFindByIdWithValidIdAndAdminRole() throws Exception {
        when(reservationService.findById(1L)).thenReturn(mockReservationDTO);
        when(jwtHeaderValidator.isAdminFromRequest(any(HttpServletRequest.class))).thenReturn(true);

        mockMvc.perform(get(BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    // @Test
    // void testFindByIdWithValidIdAndCustomerRoleCorrectCustomerId() throws Exception {
    //     when(reservationService.findById(1L)).thenReturn(mockReservationDTO);
    //     when(jwtHeaderValidator.validateRequest(any(HttpServletRequest.class), anyLong())).thenReturn(true);

    //     mockMvc.perform(get(BASE_URL + "1")
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }

    @Test
    void testFindByIdWithValidIdAndCustomerRoleIncorrectCustomerId() throws Exception {
        when(reservationService.findById(1L)).thenReturn(mockReservationDTO);
        when(jwtHeaderValidator.validateRequest(any(HttpServletRequest.class), anyLong())).thenReturn(false);

        mockMvc.perform(get(BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testFindByIdWithNonExistingId() throws Exception {
        when(reservationService.findById(1L)).thenReturn(null);

        mockMvc.perform(get(BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByIdWithoutAuthentication() throws Exception {
        when(jwtHeaderValidator.isAdminFromRequest(any(HttpServletRequest.class))).thenReturn(false);
        when(jwtHeaderValidator.validateRequest(any(HttpServletRequest.class), anyLong())).thenReturn(false);

        mockMvc.perform(get(BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
}
