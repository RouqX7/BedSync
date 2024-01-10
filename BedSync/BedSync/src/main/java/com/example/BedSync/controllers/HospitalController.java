package com.example.BedSync.controllers;

import com.example.BedSync.models.Bed;
import com.example.BedSync.models.Ward;
import com.example.BedSync.services.BedService;
import com.example.BedSync.services.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital/")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class HospitalController {

    @Autowired
    private WardService wardService;

    @Autowired
    private BedService bedService;

    // Endpoint to fetch a specific ward
    @GetMapping("/wards/{wardId}")
    public ResponseEntity<Ward> getWard(@PathVariable String wardId) {
        return wardService.getWardById(wardId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to fetch beds for a specific ward
    @GetMapping("/wards/{wardId}/beds")
    public ResponseEntity<List<Bed>> getBedsByWard(@PathVariable String wardId) {
        List<Bed> beds = bedService.getBedsByWard(wardId);
        return ResponseEntity.ok(beds);
    }

    // Other endpoints...
}