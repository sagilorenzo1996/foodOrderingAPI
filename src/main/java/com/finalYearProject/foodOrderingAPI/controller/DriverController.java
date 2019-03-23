package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.Driver;
import com.finalYearProject.foodOrderingAPI.domain.Login;
import com.finalYearProject.foodOrderingAPI.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    DriverRepository driverRepository;

    @PostMapping("/")
    public Long createDriver(@RequestBody Driver driver){
        return driverRepository.save(driver).getId();
    }

    @PostMapping("/{id}")
    public Optional<Driver> getDriver(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Driver> driver = driverRepository.findById(id);
        if(!driver.isPresent()){
            throw new ResourceNotFoundException("Driver not found");
        }
        return driver;
    }

    @DeleteMapping("/{id}")
    public String  deleteDriver(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Driver> driver = driverRepository.findById(id);
        if(!driver.isPresent()){
            throw new ResourceNotFoundException("Driver not found");
        }
        driverRepository.delete(driver.get());
        return "Driver Deleted";
    }

    @PutMapping("/{id}")
    public Long updateDriver(@PathVariable Long id, @Valid @RequestBody Driver updatedDriver) throws ResourceNotFoundException {
        Optional<Driver> driver = driverRepository.findById(id);
        if(!driver.isPresent()) {
            throw new ResourceNotFoundException("Driver not found");
        }
        driver.get().setEmail(updatedDriver.getEmail());
        driver.get().setFirstName(updatedDriver.getFirstName());
        driver.get().setLastName(updatedDriver.getLastName());
        driver.get().setTelephone(updatedDriver.getTelephone());
        driver.get().setStatus(updatedDriver.getStatus());
        driver.get().setPlateNo(updatedDriver.getPlateNo());
        driver.get().setPassword(updatedDriver.getPassword());
        return driverRepository.save(driver.get()).getId();

    }

    @PostMapping("/login")
    public Object login(@RequestBody Login login) throws ResourceNotFoundException {
        try {
            Long id = Long.parseLong(login.getTelephone());
            Optional<Driver> driver = driverRepository.findById(id);
            if(!driver.isPresent()){
                throw new ResourceNotFoundException("Driver Not Found");
            }
            if(driver.get().getPassword().equals(login.getPassword())){
                return driver.get();
            }
            return -1;
        }
        catch (Exception e){
            return e.toString();
        }
    }
}
