package com.example.TaxiService.controller;
import com.example.TaxiService.model.Driver;
import com.example.TaxiService.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping
    public String listDrivers(Model model) {
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "driver-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "driver-form";
    }

    @PostMapping("/add")
    public String addDriver(Driver driver) {
        driverService.saveDriver(driver);
        return "redirect:/drivers";
    }

    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return "redirect:/drivers";
    }

    @GetMapping("/{id}")
    public String getDriverById(@PathVariable Long id, Model model) {
        model.addAttribute("driver", driverService.getDriverById(id));
        return "driver-detail"; // додайте цю сторінку для перегляду деталей водія
    }
}
