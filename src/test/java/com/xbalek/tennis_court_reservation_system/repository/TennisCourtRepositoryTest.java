// package com.xbalek.tennis_court_reservation_system.repository;

// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.any;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import static org.mockito.Mockito.when;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.xbalek.tennis_court_reservation_system.dto.ReservationDTO;
// import com.xbalek.tennis_court_reservation_system.model.Reservation;
// import com.xbalek.tennis_court_reservation_system.model.TennisCourt;

// import jakarta.persistence.EntityManager;

// @SpringBootTest
// public class TennisCourtRepositoryTest {
    
//     @Mock
//     private EntityManager entityManager;

//     @InjectMocks
//     private TennisCourtRepository tennisCourtRepository;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//         @Test
//     void testFindById() {
//         TennisCourt mockCourt = new TennisCourt();
//         when(entityManager.find(TennisCourt.class, 1L)).thenReturn(mockCourt);
//         TennisCourt result = tennisCourtRepository.findById(1L);
//         assertEquals(mockCourt, result);
//     }

//     @Test
//     void testCreate() {
//         TennisCourt newCourt = new TennisCourt();
//         Mockito.doNothing().when(entityManager).persist(any(TennisCourt.class));
//         TennisCourt result = tennisCourtRepository.create(newCourt, 1L);
//         assertNotNull(result);
//     }

//     @Test
//     void testUpdate() {
//         TennisCourt updatedCourt = new TennisCourt();
//         when(entityManager.merge(any(TennisCourt.class))).thenReturn(updatedCourt);
//         TennisCourt result = tennisCourtRepository.update(updatedCourt);
//         assertEquals(updatedCourt, result);
//     }

//     @Test
//     void testDelete() {
//         TennisCourt mockCourt = new TennisCourt();
//         when(entityManager.find(TennisCourt.class, 1L)).thenReturn(mockCourt);
//         TennisCourt result = tennisCourtRepository.delete(1L);
//         assertTrue(result.getIsDeleted());
//     }

//     // @Test
//     // void testIsCourtReserved() {
//     //     when(entityManager.createQuery(any(String.class))).thenReturn(query);
//     //     when(query.getSingleResult()).thenReturn(1L);
//     //     boolean result = tennisCourtRepository.isCourtReserved(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
//     //     assertTrue(result);
//     // }

//     @Test
//     void testGetReservations() {
//         // Assuming ReservationDTO and reservationMapper.toDTOList are properly mocked/setup
//         List<ReservationDTO> result = Arrays.asList(tennisCourtRepository.getReservations(1L));
//         assertNotNull(result);
//     }

//     @Test
//     void testAddReservation() {
//         TennisCourt court = new TennisCourt();
//         Reservation reservation = new Reservation();
//         when(entityManager.find(TennisCourt.class, 1L)).thenReturn(court);
//         when(entityManager.find(Reservation.class, 1L)).thenReturn(reservation);
//         assertDoesNotThrow(() -> tennisCourtRepository.addReservation(1L, 1L));
//     }

//     // @Test
//     // void testGetAll() {
//     //     List<TennisCourt> courts = Arrays.asList(new TennisCourt(), new TennisCourt());
//     //     when(entityManager.createQuery(any(String.class))).thenReturn(query);
//     //     when(query.getResultList()).thenReturn(courts);
//     //     TennisCourt[] result = tennisCourtRepository.getAll();
//     //     assertEquals(2, result.length);
//     // }
// }
