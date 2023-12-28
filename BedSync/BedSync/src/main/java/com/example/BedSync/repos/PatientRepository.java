package com.example.BedSync.repos;

import com.example.BedSync.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, Long> {
    // Add custom query methods if needed

}
