package com.example.BedSync.states;

import com.example.BedSync.models.Bed;

public interface BedState {
    void handleStateChange(Bed bed);
    String getStatus();
}
