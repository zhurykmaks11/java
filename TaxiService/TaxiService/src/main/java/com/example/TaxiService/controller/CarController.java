package com.example.TaxiService.controller;


import com.example.TaxiService.model.Car;
import com.example.TaxiService.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/cars")

public class CarController {
    @Autowired
    private CarService carService;
//    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String listCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "car-list";
    }

    @GetMapping("/{id}")
    public String getCarById(@PathVariable Long id, Model model) {
        model.addAttribute("car", carService.getCarById(id));
        return "car-detail";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "car-form";
    }

    // Метод для відображення форми додавання автомобіля
    @GetMapping("/add-car")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "add-car"; // Повертаємо ім'я шаблону
    }

    // Метод для обробки форми додавання автомобіля
    @PostMapping("/add-car")
    public String saveCar(Car car) {
        carService.saveCar(car); // Зберігаємо автомобіль через сервіс
        return "redirect:/cars"; // Перенаправляємо на список автомобілів
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/cars";
    }
}
