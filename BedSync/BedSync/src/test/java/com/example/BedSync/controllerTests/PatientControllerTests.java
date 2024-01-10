//package com.example.BedSync.controllerTests;
//
//import com.example.BedSync.controllers.PatientController;
//import com.example.BedSync.models.Bed;
//import com.example.BedSync.models.Patient;
//import com.example.BedSync.repos.BedRepository;
//import com.example.BedSync.services.PatientService;
//import com.example.BedSync.states.AvailableState;
//import com.example.BedSync.states.BedState;
//import com.example.BedSync.states.OccupiedState;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class PatientControllerTests {
//
//    @InjectMocks
//    private PatientController patientController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PatientService patientService;
//
//    @MockBean
//    private BedRepository bedRepository;
//
//    private static final Logger logger = LoggerFactory.getLogger(PatientControllerTests.class);
//
//
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testRegisterNewPatient() {
//        // Mock patient data
//        Patient mockPatient = new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "101");
//
//        // Mock the behavior of the patient service
//        when(patientService.registerPatient(any(Patient.class))).thenReturn(mockPatient);
//
//        // Test registering a new patient
//        ResponseEntity<Patient> response = patientController.registerNewPatient(mockPatient);
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(mockPatient, response.getBody());
//        verify(patientService, times(1)).registerPatient(any(Patient.class));
//    }
//
//    @Test
//    public void testGetAllPatients() {
//        // Mock patient data
//        List<Patient> mockPatients = new ArrayList<>();
//        mockPatients.add(new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "101"));
//
//        // Mock the behavior of the patient service
//        when(patientService.getAllPatients()).thenReturn(mockPatients);
//
//        // Test getting all patients
//        ResponseEntity<List<Patient>> response = patientController.getAllPatients();
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockPatients, response.getBody());
//        verify(patientService, times(1)).getAllPatients();
//    }
//
//    @Test
//    public void testGetPatientById() {
//        // Mock patient data
//        String patientId = "1";
//        Patient mockPatient = new Patient("1", "John", "Doe", new Date(), "123-456-7890", "Medical history", "101");
//
//        // Mock the behavior of the patient service
//        when(patientService.getPatientById(patientId)).thenReturn(mockPatient);
//
//        // Test getting a patient by ID
//        ResponseEntity<Patient> response = patientController.getPatientById(patientId);
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockPatient, response.getBody());
//        verify(patientService, times(1)).getPatientById(patientId);
//    }
//
//    @Test
//    public void testUpdatePatient() {
//        // Mock patient data
//        String patientId = "1";
//        Patient mockPatient = new Patient(patientId, "John", "Doe", new Date(), "123-456-7890", "Medical history", "101");
//
//        // Mock the behavior of the patient service
//        when(patientService.getPatientById(patientId)).thenReturn(mockPatient);
//
//        // Test updating a patient
//        ResponseEntity<Patient> response = patientController.updatePatient(patientId, mockPatient);
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockPatient, response.getBody());
//        verify(patientService, times(1)).getPatientById(patientId);
//    }
//
//    @Test
//    public void testDeletePatient() {
//        // Mock patient ID
//        String patientId = "1";
//
//        // Mock the behavior of the patient service
//        doNothing().when(patientService).deletePatient(patientId);
//
//        // Test deleting a patient
//        ResponseEntity<Void> response = patientController.deletePatient(patientId);
//        assertNotNull(response);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(patientService, times(1)).deletePatient(patientId);
//    }
//
////    @Test
////    public void testAssignPatientToBed() {
////        // Arrange: Create mock data and set up expected behavior
////        String patientId = "101";
////        String bedId = "101";
////        logger.debug("Test input - patientId: {}, bedId: {}", patientId, bedId);
////
////        Patient mockPatient = new Patient(patientId, "John", "Doe", new Date(), "123-456-7890", "Medical history", bedId);
////
////        // Mock the behavior of the patient service
////        when(patientService.assignPatientToBed(patientId, bedId)).thenReturn(mockPatient);
////
////        // Mock the behavior of the bed repository
////        BedState availableState = new AvailableState();
////        Bed mockBed = new Bed(bedId, "Ward1", true, "B101", "Standard", availableState, patientId,new LocalDateTime());
////        when(bedRepository.findById(bedId)).thenReturn(Optional.of(mockBed));
////
////        // Act: Call the controller method being tested
////        ResponseEntity<Patient> response = patientController.assignPatientToBed(patientId, bedId);
////
////        // Assert: Check the response and verify interactions
////        assertNotNull(response);
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////
////        // Check if the response body is not null before accessing its properties
////        if (response.getBody() != null) {
////            assertEquals(mockPatient, response.getBody());
////            assertEquals(patientId, response.getBody().getBedId());
////        } else {
////            fail("Response body is null");
////        }
////
////        // Verify that the expected interactions with mocks occurred
////        verify(patientService, times(1)).assignPatientToBed(patientId, bedId);
////        verify(bedRepository, times(1)).findById(bedId);
////    }
//
//
//    @Test
//    public void testAssignPatientToBed_Simplified() {
//        // Arrange: Mock the behavior of the bed repository
//        String bedId = "101";
//        Bed mockBed = new Bed(bedId, "Ward1", true, "B101", "Standard", new AvailableState(), "1");
//        when(bedRepository.findById(bedId)).thenReturn(Optional.of(mockBed));
//
//        // Act: Call the controller method
//        ResponseEntity<Patient> response = patientController.assignPatientToBed("1", bedId);
//
//        // Assert: Check the response and verify the interaction
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("1", response.getBody().getBedId());
//        assertFalse(mockBed.isAvailable());
//
//        // Verify that the bed repository method was called
//        verify(bedRepository, times(1)).findById(bedId);
//    }
//
//
//
//
//    @Test
//    public void testUnassignPatientFromBed() {
//        // Mock patient ID
//        String patientId = "1";
//
//        // Mock patient data
//        Patient mockPatient = new Patient(patientId, "John", "Doe", new Date(), "123-456-7890", "Medical history", "101");
//        BedState occupiedState = new OccupiedState();
//        Bed mockBed = new Bed("101", "Ward1", false, "B101", "Standard", occupiedState,"1");
//        // Mock the behavior of the patient service
//        when(patientService.unassignPatientFromBed(patientId)).thenReturn(mockPatient);
//
//        // Test unassigning a patient from a bed
//        ResponseEntity<Patient> response = patientController.unAssignPatientFromBed(patientId);
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockPatient, response.getBody());
//        verify(patientService, times(1)).unassignPatientFromBed(patientId);
//    }
//
//
//    // Add more test cases for other controller endpoints
//
//    // Test cases for assignPatientToBed and unAssignPatientFromBed
//}
