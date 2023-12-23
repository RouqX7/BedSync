package com.example.BedSync.states;

import com.example.BedSync.models.Bed;

public class CleanState implements BedState{
    @Override
    public void handleStateChange(Bed bed) {
        bed.setAvailable(true);
    }

    @Override
    public String getStatus() {
        return "Clean";
    }
}
