package com.example.BedSync.controllers;

import com.example.BedSync.models.Bed;
import com.example.BedSync.models.Patient;
import com.example.BedSync.services.BedService;
import com.example.BedSync.services.PatientService;
import com.example.BedSync.states.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds/")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class BedController {
    @Autowired
    private BedService bedService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Bed>> getAllBeds() {
        return ResponseEntity.ok(bedService.getAllBeds());
    }

    @PostMapping
    public ResponseEntity<Bed> createBed(@RequestBody Bed bed) {
        return ResponseEntity.ok(bedService.createRoom(bed));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bed> getBedById(@PathVariable String id) {
        return bedService.getBedById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bed> updateBed(@PathVariable String id, @RequestBody Bed bed) {
        bed.setId(id);
        Bed updatedBed = bedService.saveOrUpdate(bed);
        return ResponseEntity.ok(updatedBed);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBed(@PathVariable String id) {
        bedService.deleteBed(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<?> updateBedState(@PathVariable String id, @RequestParam String state) {
        try {
            BedState bedState;
            switch (state.toUpperCase()) {
                case "AVAILABLE":
                    bedState = new AvailableState();
                    break;
                case "OCCUPIED":
                    bedState= new OccupiedState();
                    break;
                case "DIRTY":
                    bedState= new DirtyState();
                    break;
                case "CLEAN":
                    bedState= new CleanState();
                    break;
                // Other cases for different states
                default:
                    throw new IllegalArgumentException("Invalid bed state");
            }
            bedService.updateBedState(id, state, bedState);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ward/{wardId}")
    public ResponseEntity<List<Bed>> getBedsByWard(@PathVariable String wardId) {
        List<Bed> beds = bedService.getBedsByWard(wardId);
        return ResponseEntity.ok(beds);
    }

    @PostMapping("/beds/admit/{patientId}/{bedId}")
    public ResponseEntity<Patient> admitPatientToBed(@PathVariable String patientId, @PathVariable String bedId) {
        Patient admittedPatient = patientService.admitPatientToBed(patientId, bedId);
        return ResponseEntity.ok(admittedPatient);
    }

    @GetMapping("/getByTimestampRange")
    public List<Bed> getBedsByTimestampRange(
            @RequestParam String startTimestamp,
            @RequestParam String endTimestamp
    ) {
        return bedService.getBedsByTimestampRange(startTimestamp, endTimestamp);
    }

}