package com.example.BedSync.services;

import com.example.BedSync.models.Bed;
import com.example.BedSync.models.Ward;
import com.example.BedSync.repos.BedRepository;
import com.example.BedSync.repos.WardRepository;
import com.example.BedSync.states.AvailableState;
import com.example.BedSync.states.DirtyState;
import com.example.BedSync.states.OccupiedState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedService {
    @Autowired
    private BedRepository bedRepository;
    @Autowired
    private WardRepository wardRepository;

    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    public Bed createRoom(Bed bed) {
        return bedRepository.save(bed);
    }
    public Optional<Bed> getById(String id) {
        return bedRepository.findById(id);
    }
    public Bed saveOrUpdate(Bed bed) {
        return bedRepository.save(bed);
    }
    public void deleteBed(String id) {
        bedRepository.deleteById(id);
    }

    public void updateBedState(String bedId, String state) {
        Bed bed = bedRepository.findById(bedId).orElseThrow(() -> new RuntimeException("Bed not found"));

        switch (state.toUpperCase()) {
            case "AVAILABLE":
                bed.setState(new AvailableState());
                break;
            case "OCCUPIED":
                bed.setState(new OccupiedState());
                break;
            case "DIRTY":
                bed.setState(new DirtyState());
                break;
            // ... other states as needed ...
            default:
                throw new IllegalArgumentException("Invalid bed state");
        }

        bedRepository.save(bed);
        // Update ward's available bed count if necessary
        updateWardAvailableBeds(bed.getWardId());
    }
    private void updateWardAvailableBeds(String wardId) {
        List<Bed> bedsInWard = bedRepository.findByWardId(wardId);
        int availableBedCount = (int) bedsInWard.stream()
                .filter(Bed::isAvailable)
                .count();

        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Ward not found with id: " + wardId));
        ward.setAvailableBeds(availableBedCount);
        wardRepository.save(ward);
    }


    public List<Bed> getBedsByWard(String wardId) {
        return bedRepository.findByWardId(wardId);
    }
    public void updateBedStatus(String bedId, boolean isAvailable) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new RuntimeException("Bed not found with id: " + bedId));

        bed.setAvailable(isAvailable);
        bedRepository.save(bed);
        Ward ward = wardRepository.findById(bed.getWardId())
                .orElseThrow(() -> new RuntimeException("Bed not found with id: " + bedId));

        if (isAvailable) {
            ward.setAvailableBeds(ward.getAvailableBeds() + 1);
        } else {
            ward.setAvailableBeds(ward.getAvailableBeds() -1);
        }
        wardRepository.save(ward);
    }



    // Other methods for bed management
}