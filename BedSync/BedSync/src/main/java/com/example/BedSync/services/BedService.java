package com.example.BedSync.services;

import com.example.BedSync.models.Bed;
import com.example.BedSync.models.Ward;
import com.example.BedSync.repos.BedRepository;
import com.example.BedSync.repos.WardRepository;
import com.example.BedSync.states.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedService {
    private final BedRepository bedRepository;
    private final WardRepository wardRepository;

    @Autowired
    public BedService(BedRepository bedRepository, WardRepository wardRepository) {
        this.bedRepository = bedRepository;
        this.wardRepository = wardRepository;
    }
    public List<Bed> getAllBeds() {
        return bedRepository.findAll();
    }

    public Bed createRoom(Bed bed) {
        return bedRepository.save(bed);
    }
    public Optional<Bed> getBedById(String id) {
        return bedRepository.findById(id);
    }
    public Bed saveOrUpdate(Bed bed) {
        return bedRepository.save(bed);
    }
    public void deleteBed(String id) {
        bedRepository.deleteById(id);
    }

    public void updateBedState(String bedId, String newState, BedState bedState) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new RuntimeException("Bed not found with id: " + bedId));

        bed.setState(bedState);
        bedRepository.save(bed);

        updateWardAvailableBeds(bed.getWardId(), newState);
    }

    private void updateWardAvailableBeds(String wardId, String newState) {
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Ward not found with id: " + wardId));

        int currentAvailableBeds = ward.getAvailableBeds();

        switch (newState.toUpperCase()) {
            case "OCCUPIED":
            case "DIRTY":
                ward.setAvailableBeds(Math.max(0, currentAvailableBeds - 1));
                break;
            case "AVAILABLE":
            case "CLEAN":
                ward.setAvailableBeds(Math.min(ward.getTotalBeds(), currentAvailableBeds + 1));
                break;
            default:
                throw new IllegalArgumentException("Invalid bed state: " + newState);
        }

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