package com.example.BedSync.repos;

import com.example.BedSync.models.Bed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BedRepository extends MongoRepository<Bed, String> {
    List<Bed> findByWardId(String wardId);
    List<Bed> findByTimestampBetween(String startTimestamp, String endTimestamp);
}
