package com.example.TaxiService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;
    private String model;
    private int year;
    private String fuel_type;
    private String body_type;
    private String color;
    private String license_plate;
    private int passenger_Capacity;
}
