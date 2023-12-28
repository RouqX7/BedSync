package com.example.BedSync;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.BedSync.factories.GeneralWard;
import com.example.BedSync.factories.ICUWard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.BedSync.models.Ward;
import com.example.BedSync.repos.WardRepository;
import com.example.BedSync.services.WardService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class WardServiceTest {

    @Mock
    private WardRepository wardRepository;

    @InjectMocks
    private WardService wardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllWards() {
        Ward ward1 = new GeneralWard("1", "General Ward", 20, "General ward description", "Active", 10, "General Medicine", 20, 10);
        Ward ward2 = new ICUWard("2", "ICU", 10, "ICU ward description", "Active", 5, "Critical Care", 10, 5);
        when(wardRepository.findAll()).thenReturn(Arrays.asList(ward1, ward2));

        List<Ward> wards = wardService.getAllWards();

        assertNotNull(wards);
        assertEquals(2, wards.size());
        verify(wardRepository, times(1)).findAll();
    }

    @Test
    public void testGetWardById() {
        Ward ward = new GeneralWard("1", "General Ward", 20, "Description", "Active", 10, "General Medicine", 20, 10);
        when(wardRepository.findById("1")).thenReturn(Optional.of(ward));

        Optional<Ward> found = wardService.getWardById("1");

        assertTrue(found.isPresent());
        assertEquals("General Ward", found.get().getName());
        verify(wardRepository, times(1)).findById("1");
    }

    @Test
    public void testSaveOrUpdateWard() {
        Ward ward = new GeneralWard("1", "General Ward", 20, "Description", "Active", 10, "General Medicine", 20, 10);
        when(wardRepository.save(any(Ward.class))).thenReturn(ward);

        Ward savedWard = wardService.saveOrUpdateWard(ward);

        assertNotNull(savedWard);
        assertEquals("General Ward", savedWard.getName());
        verify(wardRepository, times(1)).save(any(Ward.class));
    }

    @Test
    public void testDeleteWard() {
        doNothing().when(wardRepository).deleteById("1");

        wardService.deleteWard("1");

        verify(wardRepository, times(1)).deleteById("1");
    }

}