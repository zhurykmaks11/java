package com.example.TaxiService.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Norder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double distance;

    private Integer driver_id;
    private Integer client_id;

    public void addAttribute(String reports, List<Norder> reports1) {
    }
}
