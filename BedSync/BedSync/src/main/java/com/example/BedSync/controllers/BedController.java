package com.example.BedSync.controllers;

import com.example.BedSync.models.Bed;
import com.example.BedSync.services.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class BedController {
    @Autowired
    private BedService bedService;

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
        return bedService.getById(id)
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
            bedService.updateBedState(id, state);
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

}
