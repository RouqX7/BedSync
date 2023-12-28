package com.example.BedSync.services;

import com.example.BedSync.models.Bed;
import com.example.BedSync.models.Patient;
import com.example.BedSync.repos.BedRepository;
import com.example.BedSync.repos.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    BedRepository bedRepository;

    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public Patient saveOrUpdatePatient(Patient updatedPatient) {
        patientRepository.save(updatedPatient);
        return updatedPatient;
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(Long.valueOf(id));
    }

    public Patient admitPatientToBed(String patientId, String bedId) {
        Patient patient = getPatientById(patientId);
        patient.setBedId(bedId);
        return saveOrUpdatePatient(patient);
    }

    public Patient assignPatientToBed(String patientId, String bedId) {
        Patient patient = getPatientById(patientId);
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new EntityNotFoundException("Bed not found with ID: " + bedId));

        // Check if the bed is available
        if (!bed.isAvailable()) {
            throw new RuntimeException("Bed is not available for asignment " + bedId);

        }

        // Assign the patient to the bed
        patient.setBedId(bedId);
        bed.setPatientId(patientId);
        bed.setAvailable(false);

        patientRepository.save(patient);
        bedRepository.save(bed);

        return patient;
    }

    public Patient unassignPatientFromBed(String patientId) {
        Patient patient = getPatientById(patientId);

        if (patient.getBedId() == null) {
            throw new RuntimeException("Patient is not assigned to a bed " + patientId);

        }

        Bed bed = bedRepository.findById(patient.getBedId())
                .orElseThrow(() -> new EntityNotFoundException("Bed not found with ID: " + patient.getBedId()));

        // Unassign the patient from the bed
        patient.setBedId(null);
        bed.setPatientId(null);
        bed.setAvailable(true);

        patientRepository.save(patient);
        bedRepository.save(bed);

        return patient;
    }

}
