//package com.example.BedSync.serviceTests;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.example.BedSync.factories.GeneralWard;
//import com.example.BedSync.models.Ward;
//import com.example.BedSync.repos.WardRepository;
//import com.example.BedSync.states.BedState;
//import com.example.BedSync.states.OccupiedState;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.BedSync.models.Bed;
//import com.example.BedSync.repos.BedRepository;
//import com.example.BedSync.services.BedService;
//import com.example.BedSync.states.AvailableState;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//public class BedServiceTest {
//    @MockBean
//    private BedRepository bedRepository;
//
//    @Autowired
//    private BedService bedService;
//
//    @MockBean
//    private WardRepository wardRepository; // Ensure this is mocked
//
//    @Captor
//    private ArgumentCaptor<Bed> bedCaptor;
//
//    @Captor
//    private ArgumentCaptor<Ward> wardCaptor;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllBeds() {
//        BedState availableState = new AvailableState();
//
//        Bed bed1 = new Bed("1", "Ward1", true, "B101", "Standard", availableState,"1");
//        Bed bed2 = new Bed("2", "Ward1", false, "B102", "Standard", availableState,"1");
//        when(bedRepository.findAll()).thenReturn(Arrays.asList(bed1, bed2));
//
//        List<Bed> beds = bedService.getAllBeds();
//
//        assertNotNull(beds);
//        assertEquals(2, beds.size());
//        verify(bedRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetBedById() {
//        BedState availableState = new AvailableState();
//        Bed bed = new Bed("1", "Ward1", true, "B101", "Standard", availableState,"1");
//        when(bedRepository.findById("1")).thenReturn(Optional.of(bed));
//
//        Optional<Bed> found = bedService.getBedById("1");
//
//        assertTrue(found.isPresent());
//        assertEquals("1", found.get().getId());
//        assertEquals("Ward1", found.get().getWardId());
//        assertEquals("B101", found.get().getBedNumber());
//        assertEquals("Standard", found.get().getBedType());
//        assertTrue(found.get().isAvailable());
//        assertEquals("AVAILABLE", found.get().getState().getStatus());
//        verify(bedRepository, times(1)).findById("1");
//    }
//
//    @Test
//    public void testSaveOrUpdateBed() {
//        BedState availableState = new AvailableState();
//        Bed bed = new Bed("1", "Ward1", true, "B101", "Standard", availableState,"1");
//        when(bedRepository.save(any(Bed.class))).thenReturn(bed);
//
//        Bed savedBed = bedService.saveOrUpdate(bed);
//
//        assertNotNull(savedBed);
//        assertEquals("1", savedBed.getId());
//        verify(bedRepository, times(1)).save(any(Bed.class));
//    }
//
//    @Test
//    public void testDeleteBed() {
//        doNothing().when(bedRepository).deleteById("1");
//
//        bedService.deleteBed("1");
//
//        verify(bedRepository, times(1)).deleteById("1");
//    }
//
//    @Test
//    public void testUpdateBedState() {
//        BedState availableState = new AvailableState();
//        BedState occupiedState = new OccupiedState();
//        Bed bed = new Bed("1", "Ward1", true, "B101", "Standard", availableState,"1");
//        when(bedRepository.findById("1")).thenReturn(Optional.of(bed));
//        when(bedRepository.save(any(Bed.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        int initialAvailableBeds = 5;
//        Ward ward = new GeneralWard("Ward1", "General Ward", 10, "Description", "Active", initialAvailableBeds, "Department", 10, initialAvailableBeds);
//        when(wardRepository.findById("Ward1")).thenReturn(Optional.of(ward));
//        when(wardRepository.save(any(Ward.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        bedService.updateBedState("1", "OCCUPIED", occupiedState);
//
//        verify(bedRepository, times(1)).findById("1");
//        verify(bedRepository, times(1)).save(bedCaptor.capture());
//
//        Bed updatedBed = bedCaptor.getValue();
//        assertEquals("OCCUPIED", updatedBed.getState().getStatus());
//
//        int expectedAvailableBeds = initialAvailableBeds - 1;
//        verify(wardRepository, times(1)).save(wardCaptor.capture());
//        Ward updatedWard = wardCaptor.getValue();
//        assertEquals(expectedAvailableBeds, updatedWard.getAvailableBeds());
//    }
//
//    // ... more tests as needed ...
//}
