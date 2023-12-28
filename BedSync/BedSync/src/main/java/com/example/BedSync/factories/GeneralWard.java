package com.example.BedSync.factories;

import com.example.BedSync.models.Ward;

public class GeneralWard extends Ward {
    public GeneralWard(String id, String name, int capacity, String description, String status, int currentOccupancy, String responsibleDepartment,int totalBeds, int availableBeds) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.status = status;
        this.currentOccupancy = currentOccupancy;
        this.responsibleDepartment = responsibleDepartment;
        this.totalBeds = totalBeds;
        this.availableBeds = availableBeds;
    }

    @Override
    public String getWardType() {
        return "General";
    }

}

// Other specific Ward types like PediatricsWard, etc.
