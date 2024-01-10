package com.example.BedSync.controllers;

import com.example.BedSync.models.Patient;
import com.example.BedSync.repos.BedRepository;
import com.example.BedSync.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private BedRepository bedRepository;

    private final Logger logger =  LoggerFactory.getLogger(PatientController.class);


    @PostMapping("/register")
    public ResponseEntity<Patient> registerNewPatient(@RequestBody Patient patient) {
        Patient registeredPatient = patientService.registerPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredPatient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient updatedPatient) {
        Optional<Patient> existingPatient = Optional.ofNullable(patientService.getPatientById(id));

        if (existingPatient.isPresent()) {
            updatedPatient.setId(id);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{patientId}/assign/{bedId}")
    public ResponseEntity<Patient> assignPatientToBed(@PathVariable String patientId, @PathVariable String bedId) {
        Patient patient = patientService.assignPatientToBed(patientId, bedId);
        logger.debug("Received bedId: {}", bedId);

        return ResponseEntity.ok(patient);
    }

    @PostMapping("/{patientId}/unassign")
    public ResponseEntity<Patient> unAssignPatientFromBed(@PathVariable String patientId) {
        Patient patient = patientService.unassignPatientFromBed(patientId);
        return ResponseEntity.ok(patient);
    }
}