package com.example.BedSync.states;

import com.example.BedSync.models.Bed;

public class DirtyState implements BedState {
    @Override
    public void handleStateChange(Bed bed) {
        bed.setAvailable(false);
    }

    @Override
    public String getStatus() {
        return "DIRTY";
    }
}

