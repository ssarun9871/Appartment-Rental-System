package com.wipro.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wipro.demo.entity.Flat;
import com.wipro.demo.entity.Landlord;
import com.wipro.demo.repository.FlatRepository;
import com.wipro.demo.repository.LandlordRepository;

class FlatServiceTest {

    @Mock
    private FlatRepository flatRepository;

    @Mock
    private LandlordRepository landlordRepository;

    @InjectMocks
    private FlatService flatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFlat_Success() {
        Flat flat = new Flat();
        Landlord landlord = new Landlord();
        landlord.setLandlord_id(1);

        when(landlordRepository.findById(1)).thenReturn(Optional.of(landlord));
        when(flatRepository.save(flat)).thenAnswer(invocation -> {
            Flat savedFlat = invocation.getArgument(0);
            savedFlat.setFlat_id(1);
            return savedFlat;
        });

        ResponseEntity<Object> response = flatService.addFlat(flat, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Success", ((Map<String, Object>) response.getBody()).get("message"));
        
        assertEquals(flat, ((Map<String, Object>) response.getBody()).get("data"));
        
    }

    @Test
    void testAddFlat_LandlordNotFound() {
        Flat flat = new Flat();

        when(landlordRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = flatService.addFlat(flat, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Landlord not found", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testGetAllFlats() {
        Flat flat1 = new Flat();
        Flat flat2 = new Flat();

        when(flatRepository.findAll()).thenReturn(Arrays.asList(flat1, flat2));

        List<Flat> flats = flatService.getAllFlats();

        assertEquals(2, flats.size());
        assertEquals(flat1, flats.get(0));
        assertEquals(flat2, flats.get(1));
    }

    @Test
    void testGetAllAvailableFlats() {
        Flat flat1 = new Flat();
        flat1.setAvailability(true);
        Flat flat2 = new Flat();
        flat2.setAvailability(true);

        when(flatRepository.findByAvailability(true)).thenReturn(Arrays.asList(flat1, flat2));

        List<Flat> flats = flatService.getAllAvailableFlats();

        assertEquals(2, flats.size());
        assertEquals(flat1, flats.get(0));
        assertEquals(flat2, flats.get(1));
    }

    @Test
    void testGetFlatById() {
        Flat flat = new Flat();
        flat.setFlat_id(1);

        when(flatRepository.findById(1)).thenReturn(Optional.of(flat));

        Optional<Flat> result = flatService.getFlatById(1);

        assertEquals(true, result.isPresent());
        assertEquals(flat, result.get());
    }

    @Test
    void testGetFlatByLandlord_Success() {
        Landlord landlord = new Landlord();
        landlord.setLandlord_id(1);
        Flat flat1 = new Flat();
        flat1.setLandlord(landlord);
        Flat flat2 = new Flat();
        flat2.setLandlord(landlord);

        when(landlordRepository.findById(1)).thenReturn(Optional.of(landlord));
        when(flatRepository.findByLandlord(landlord)).thenReturn(Arrays.asList(flat1, flat2));

        ResponseEntity<Object> response = flatService.getFlatByLandlord(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", ((Map<String, Object>) response.getBody()).get("message"));
        assertEquals(Arrays.asList(flat1, flat2), ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void testGetFlatByLandlord_NotFound() {
        when(landlordRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = flatService.getFlatByLandlord(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Landlord not found", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void testDeleteFlat_Success() {
        when(flatRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Object> response = flatService.deleteFlat(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", ((Map<String, Object>) response.getBody()).get("message"));
        verify(flatRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteFlat_NotFound() {
        when(flatRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = flatService.deleteFlat(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Flat with ID 1 does not exist.", ((Map<String, Object>) response.getBody()).get("message"));
        verify(flatRepository, times(0)).deleteById(1);
    }
}
