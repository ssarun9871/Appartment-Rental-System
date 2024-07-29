package com.wipro.demo.service;

import com.wipro.demo.dto.BookingRequestDTO;
import com.wipro.demo.entity.Booking;
import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Tenant;
import com.wipro.demo.repository.BookingRepository;
import com.wipro.demo.repository.FlatRepository;
import com.wipro.demo.repository.LandlordRepository;
import com.wipro.demo.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlatRepository flatRepository;

    @Mock
    private LandlordRepository landlordRepository;

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private BookingService bookingService;

    public BookingServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBooking_Success() {
        Flat flat = new Flat();
        flat.setFlat_id(1);
        flat.setAvailability(true);

        Tenant tenant = new Tenant();
        tenant.setTenant_id(1);

        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setFlat_id(1);
        bookingRequestDTO.setTenant_id(1);
        bookingRequestDTO.setFrom_date(LocalDate.now());
        bookingRequestDTO.setTo_date(LocalDate.now().plusDays(1));

        when(flatRepository.findById(1)).thenReturn(Optional.of(flat));
        when(tenantRepository.findById(1)).thenReturn(Optional.of(tenant));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ResponseEntity<Object> response = bookingService.addBooking(bookingRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Success", responseBody.get("message"));
        assertNotNull(responseBody.get("data"));
        Booking savedBooking = (Booking) responseBody.get("data");
        assertEquals("PENDING", savedBooking.getBooking_status());
    }

    @Test
    void testAddBooking_FlatNotAvailable() {
        Flat flat = new Flat();
        flat.setFlat_id(1);
        flat.setAvailability(false);

        Tenant tenant = new Tenant();
        tenant.setTenant_id(1);

        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setFlat_id(1);
        bookingRequestDTO.setTenant_id(1);
        bookingRequestDTO.setFrom_date(LocalDate.now());
        bookingRequestDTO.setTo_date(LocalDate.now().plusDays(1));

        when(flatRepository.findById(1)).thenReturn(Optional.of(flat));
        when(tenantRepository.findById(1)).thenReturn(Optional.of(tenant));

        ResponseEntity<Object> response = bookingService.addBooking(bookingRequestDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Flat not found, not available, or Tenant not found", responseBody.get("message"));
    }

    @Test
    void testGetAllBookings() {
        Booking booking = new Booking();
        booking.setBooking_id(1);

        List<Booking> bookings = Collections.singletonList(booking);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.getAllBookings();
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getBooking_id());
    }

    @Test
    void testGetBookingsByTenantId() {
        Booking booking = new Booking();
        booking.setBooking_id(1);

        Tenant tenant = new Tenant();
        tenant.setTenant_id(1);

        List<Booking> bookings = Collections.singletonList(booking);
        when(tenantRepository.findById(1)).thenReturn(Optional.of(tenant));
        when(bookingRepository.findByTenant(Optional.of(tenant))).thenReturn(bookings);

        List<Booking> result = bookingService.getBookingsByTenantId(1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getBooking_id());
    }

    @Test
    void testGetBookingByFlat() {
        Flat flat = new Flat();
        flat.setFlat_id(1);

        Booking booking = new Booking();
        booking.setBooking_id(1);
        List<Booking> bookings = Collections.singletonList(booking);

        when(flatRepository.findById(1)).thenReturn(Optional.of(flat));
        when(bookingRepository.findByFlat(flat)).thenReturn(bookings);

        ResponseEntity<Object> response = bookingService.getBookingByFlat(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Success", responseBody.get("message"));
        assertEquals(bookings, responseBody.get("data"));
    }

    @Test
    void testGetBookingByFlat_NotFound() {
        when(flatRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = bookingService.getBookingByFlat(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Flat not found", responseBody.get("message"));
    }

    @Test
    void testCancelBookings_Success() {
        Booking booking = new Booking();
        booking.setBooking_id(1);
        booking.setBooking_status("PENDING");

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenAnswer(invocation -> invocation.getArguments()[0]);

        String result = bookingService.cancelBookings(1);

        assertEquals("Booking cancelled successfully", result);
        assertEquals("CANCELLED", booking.getBooking_status());
    }

    @Test
    void testCancelBookings_NotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        String result = bookingService.cancelBookings(1);

        assertEquals("Booking not found", result);
    }

    @Test
    void testConfirmBookings_Success() {
        Booking booking = new Booking();
        booking.setBooking_id(1);
        booking.setBooking_status("PENDING");

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenAnswer(invocation -> invocation.getArguments()[0]);

        String result = bookingService.confirmBookings(1);

        assertEquals("Booking confirmed successfully", result);
        assertEquals("CONFIRMED", booking.getBooking_status());
    }

    @Test
    void testConfirmBookings_NotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        String result = bookingService.confirmBookings(1);

        assertEquals("Booking not found", result);
    }

    @Test
    void testRejectBookings_Success() {
        Flat flat = new Flat();
        flat.setFlat_id(1);
        flat.setAvailability(false);

        Booking booking = new Booking();
        booking.setBooking_id(1);
        booking.setBooking_status("PENDING");
        booking.setFlat(flat);  // Set the flat in the booking

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenAnswer(invocation -> invocation.getArguments()[0]);

        String result = bookingService.rejectBookings(1);

        assertEquals("Booking rejected successfully", result);
        assertEquals("REJECTED", booking.getBooking_status());
        assertEquals(true, flat.getAvailability());
    }

    @Test
    void testRejectBookings_NotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        String result = bookingService.rejectBookings(1);

        assertEquals("Booking not found", result);
    }
}
