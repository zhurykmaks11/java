package org.example.repository;


import org.example.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    // Додаткові кастомні методи для пошуку можуть бути додані тут за потреби
}
