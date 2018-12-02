package com.oocl.web.sampleWebApp.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Max(100)
    @Min(1)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    private Integer capacity;

    @Column(name = "parking_lot_id", length = 64, unique = true, nullable = false)
    private String parkingLotId;

    public Long getId() {
        return id;
    }

    public String getparkingLotId() {
        return parkingLotId;
    }

    public void setparkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    protected ParkingLot() {}

    public ParkingLot(String parkingLotId , int capacity) {
        this.parkingLotId = parkingLotId;
        this.capacity = capacity;
    }
}

