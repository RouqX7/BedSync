package com.example.BedSync.factories;

import com.example.BedSync.models.Ward;

public class WardFactory {
    public static Ward createGeneralWard(String id, String name, int capacity, String description, String status, int currentOccupancy, String responsibleDepartment, int totalBeds, int availableBeds) {
        return new GeneralWard(id, name, capacity, description, status, currentOccupancy, responsibleDepartment, totalBeds, availableBeds);
    }

    public static Ward createICUWard(String id, String name, int capacity, String description, String status, int currentOccupancy, String responsibleDepartment, int totalBeds, int availableBeds) {
        return new ICUWard(id, name, capacity, description, status, currentOccupancy, responsibleDepartment, totalBeds, availableBeds);
    }
}