package org.example.service;


import org.example.model.Car;
import org.example.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }


    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }


    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setMake(updatedCar.getMake());
        car.setModel(updatedCar.getModel());
        car.setYear(updatedCar.getYear());
        car.setFuelType(updatedCar.getFuelType());
        car.setBodyType(updatedCar.getBodyType());
        car.setColor(updatedCar.getColor());
        car.setLicensePlate(updatedCar.getLicensePlate());
        car.setPassengerCapacity(updatedCar.getPassengerCapacity());
        return carRepository.save(car);
    }

    public Car partialUpdateCar(Long id, Map<String, Object> updates) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "make" -> car.setMake((String) value);
                case "model" -> car.setModel((String) value);
                case "year" -> car.setYear((Integer) value);
                case "fuelType" -> car.setFuelType((String) value);
                case "bodyType" -> car.setBodyType((String) value);
                case "color" -> car.setColor((String) value);
                case "licensePlate" -> car.setLicensePlate((String) value);
                case "passengerCapacity" -> car.setPassengerCapacity((Integer) value);
            }
        });
        return carRepository.save(car);
    }


}

