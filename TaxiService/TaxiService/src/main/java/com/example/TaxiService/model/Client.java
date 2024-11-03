package com.example.TaxiService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String full_name;
    private LocalDate birth_date; // Залишаємо birthDate, а не dateOfBirth
    private String phone_number;
    private String password; // Нове поле
}
