package com.example.BedSync;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.BedSync.models.Bed;
import com.example.BedSync.models.Patient;
import com.example.BedSync.repos.BedRepository;
import com.example.BedSync.repos.PatientRepository;
import com.example.BedSync.services.PatientService;
import com.example.BedSync.states.AvailableState;
import com.example.BedSync.states.BedState;
import com.example.BedSync.states.OccupiedState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private BedRepository bedRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterPatient() {
        // Create a sample patient
        Patient patient = new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "B101");

        // Mock the behavior of the repository
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Register the patient
        Patient savedPatient = patientService.registerPatient(patient);

        // Verify the result
        assertNotNull(savedPatient);
        assertEquals("1", savedPatient.getId());
        assertEquals("John", savedPatient.getFirstName());
        assertEquals("Doe", savedPatient.getLastName());
     }

    @Test
    public void testGetAllPatients() {
        // Create a list of sample patients
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "B101"));
        patients.add(new Patient("2", "Jane", "Smith", new Date(), "987-654-3210", "Medical history", "B102"));

        // Mock the behavior of the repository
        when(patientRepository.findAll()).thenReturn(patients);

        // Get all patients
        List<Patient> retrievedPatients = patientService.getAllPatients();

        // Verify the result
        assertNotNull(retrievedPatients);
        assertEquals(2, retrievedPatients.size());
    }

    @Test
    public void testGetPatientById() {
        // Create a sample patient
        Patient patient = new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "B101");

        // Mock the behavior of the repository
        when(patientRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(patient));

        // Get a patient by ID
        Patient retrievedPatient = patientService.getPatientById("1");

        // Verify the result
        assertNotNull(retrievedPatient);
        assertEquals("1", retrievedPatient.getId());
        assertEquals("John", retrievedPatient.getFirstName());
        assertEquals("Doe", retrievedPatient.getLastName());
    }

    public void testAssignPatientToBed() {
        // Mock patient data
        Patient mockPatient = new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "B101");

        // Mock bed data
        BedState availableState = new AvailableState();
        Bed mockBed = new Bed("101", "Ward1", true, "B101", "Standard", availableState,"1");

        // Mock the behavior of the patient repository
        when(patientRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(mockPatient));

        // Mock the behavior of the bed repository
        when(bedRepository.findById("101")).thenReturn(Optional.of(mockBed));
        when(bedRepository.save(any(Bed.class))).thenReturn(mockBed);

        // Test assigning a patient to a bed
        Patient assignedPatient = patientService.assignPatientToBed("1", "101");
        assertNotNull(assignedPatient);
        assertEquals("101", assignedPatient.getBedId());
        assertFalse(mockBed.isAvailable());
        // Verify that the patient and bed repositories were called correctly
        verify(patientRepository, times(1)).findById(Long.valueOf("1"));
        verify(bedRepository, times(1)).findById("101");
        verify(bedRepository, times(1)).save(any(Bed.class));
    }

    @Test
    public void testUnassignPatientFromBed() {
        // Mock patient data
        Patient mockPatient = new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "101");

        // Mock bed data
        BedState occupiedState = new OccupiedState();
        Bed mockBed = new Bed("101", "Ward1", false, "B101", "Standard", occupiedState,"1");

        // Mock the behavior of the patient repository
        when(patientRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(mockPatient));

        // Mock the behavior of the bed repository
        when(bedRepository.findById("101")).thenReturn(Optional.of(mockBed));
        when(bedRepository.save(any(Bed.class))).thenReturn(mockBed);

        // Test unassigning a patient from a bed
        Patient unassignedPatient = patientService.unassignPatientFromBed("1");
        assertNotNull(unassignedPatient);
        assertNull(unassignedPatient.getBedId());
        assertTrue(mockBed.isAvailable());
        // Verify that the patient and bed repositories were called correctly
        verify(patientRepository, times(1)).findById(Long.valueOf("1"));
        verify(bedRepository, times(1)).findById("101");
        verify(bedRepository, times(1)).save(any(Bed.class));
    }

    // Add more test methods for other service operations (create, update, delete, etc.)
}
