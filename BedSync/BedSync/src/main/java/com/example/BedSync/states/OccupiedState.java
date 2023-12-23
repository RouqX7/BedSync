package com.example.BedSync.states;

import com.example.BedSync.models.Bed;

public class OccupiedState implements BedState {
    @Override
    public void handleStateChange(Bed bed) {
        // Logic for when a bed is occupied
        bed.setAvailable(false);
    }

    @Override
    public String getStatus() {
        return "Occupied";
    }
}