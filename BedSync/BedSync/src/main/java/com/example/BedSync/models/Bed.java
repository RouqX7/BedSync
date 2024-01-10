package com.example.BedSync.models;

import com.example.BedSync.states.BedState;
import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Entity
@Document(collection = "beds")
public class Bed {
    @Id
    private String id;
    private String wardId; // Reference to the Ward
    private boolean isAvailable;
    private String bedNumber;
    private String bedType;
    private BedState state;
    private String patientId; // Reference to the patient by its ID
    private LocalDateTime timestamp;


    public Bed(String id, String wardId, boolean isAvailable, String bedNumber, String bedType, BedState state, String patientId,LocalDateTime timestamp) {
        this.id = id;
        this.wardId = wardId;
        this.isAvailable = isAvailable;
        this.bedNumber = bedNumber;
        this.bedType = bedType;
        this.state = state;
        this.patientId = patientId;
        this.timestamp = timestamp;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public BedState getState() {
        return state;
    }

    public void setState(BedState state) {
        this.state = state;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Bed{" +
                "id='" + id + '\'' +
                ", wardId='" + wardId + '\'' +
                ", isAvailable=" + isAvailable +
                ", bedNumber='" + bedNumber + '\'' +
                ", bedType='" + bedType + '\'' +
                ", state=" + state +
                ", patientId='" + patientId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
