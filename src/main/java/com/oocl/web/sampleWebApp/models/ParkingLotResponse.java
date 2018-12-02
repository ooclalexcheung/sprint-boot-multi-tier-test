package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingLot;

import java.util.Objects;


public class ParkingLotResponse {
    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static ParkingLotResponse create(String parkingLotId, int capacity) {
        Objects.requireNonNull(parkingLotId);
        final ParkingLotResponse response = new ParkingLotResponse();
        response.setParkingLotId(parkingLotId);
        response.setCapacity(capacity);
        return response;
    }
    public static ParkingLotResponse create(ParkingLot entity) {
        return create(entity.getparkingLotId(), entity.getCapacity());
    }
    @JsonIgnore
    public boolean isValid() {
        return parkingLotId != null;
    }

    private String parkingLotId;
    private int capacity;
}
