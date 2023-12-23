package com.example.BedSync.states;

import com.example.BedSync.models.Bed;

public class AvailableState implements BedState {
    @Override
    public void handleStateChange(Bed bed) {
        // Logic for when a bed becomes available
        bed.setAvailable(true);
    }

    @Override
    public String getStatus() {
        return "Available";
    }
}


// Similar classes for DirtyState, UnderMaintenanceState, etc.
