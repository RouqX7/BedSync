package com.example.BedSync.controllers;

import com.example.BedSync.models.Ward;
import com.example.BedSync.services.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wards")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class WardController {

    @Autowired
    private WardService wardService;

    @GetMapping
    public ResponseEntity<List<Ward>> getAllWards() {
        return ResponseEntity.ok(wardService.getAllWards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ward> getWardById(@PathVariable String id) {
        return wardService.getWardById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ward> createWard(@RequestBody Ward ward) {
        return ResponseEntity.ok(wardService.saveOrUpdateWard(ward));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ward> updateWard(@PathVariable String id, @RequestBody Ward ward) {
        return wardService.getWardById(id)
                .map(existingWard -> {
                    ward.setId(id);
                    return ResponseEntity.ok(wardService.saveOrUpdateWard(ward));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWard(@PathVariable String id) {
        wardService.deleteWard(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints as required
}