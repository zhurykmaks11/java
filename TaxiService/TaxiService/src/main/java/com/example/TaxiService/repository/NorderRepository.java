package com.example.TaxiService.repository;

import com.example.TaxiService.model.Car;
import com.example.TaxiService.model.Norder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NorderRepository extends JpaRepository<Norder, Long> {
}
