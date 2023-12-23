package com.example.BedSync.repos;

import com.example.BedSync.models.Ward;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WardRepository extends MongoRepository<Ward, String> {
    // You can add custom methods if needed
}
