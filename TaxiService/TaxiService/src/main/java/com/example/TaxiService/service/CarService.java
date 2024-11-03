package com.example.TaxiService.service;


import com.example.TaxiService.model.Car;
import com.example.TaxiService.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    // Метод для збереження автомобіля
    public void saveCar(Car car) {
        carRepository.save(car); // Зберігаємо автомобіль у репозиторії
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
