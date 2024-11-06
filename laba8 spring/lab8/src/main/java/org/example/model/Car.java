package org.example.model;


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

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    private int year;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "body_type")
    private String bodyType;

    private String color;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    // Конструктори, геттери та сеттери згенеровані Lombok
}
