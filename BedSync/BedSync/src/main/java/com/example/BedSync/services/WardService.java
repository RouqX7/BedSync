package com.example.BedSync.services;

import com.example.BedSync.models.Ward;
import com.example.BedSync.repos.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WardService {

    @Autowired
    private WardRepository wardRepository;

    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    public Optional<Ward> getWardById(String id) {
        return wardRepository.findById(id);
    }

    public Ward saveOrUpdateWard(Ward ward) {
        return wardRepository.save(ward);
    }

    public void deleteWard(String id) {
        wardRepository.deleteById(id);
    }

    // Additional methods as per requirements
}
