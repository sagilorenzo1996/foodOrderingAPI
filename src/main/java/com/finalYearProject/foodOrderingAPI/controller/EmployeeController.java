package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.Employee;
import com.finalYearProject.foodOrderingAPI.domain.Login;
import com.finalYearProject.foodOrderingAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/")
    public Long createEmployee(@RequestBody Employee emp){
        return employeeRepository.save(emp).getId();
    }

    @PostMapping("/{id}")
    public Optional<Employee> getEmployee(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Employee> emp = employeeRepository.findById(id);
        if(!emp.isPresent()){
            throw new ResourceNotFoundException("Employee not found");
        }
        return emp;
    }

    @DeleteMapping("/{id}")
    public String  deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Employee> emp = employeeRepository.findById(id);
        if(!emp.isPresent()){
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.delete(emp.get());
        return "Employee Deleted";
    }

    @PutMapping("/{id}")
    public Long updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee updatedEmp) throws ResourceNotFoundException {
        Optional<Employee> emp = employeeRepository.findById(id);
        if(!emp.isPresent()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        emp.get().setEmail(updatedEmp.getEmail());
        emp.get().setFirstName(updatedEmp.getFirstName());
        emp.get().setLastName(updatedEmp.getLastName());
        emp.get().setPosition(updatedEmp.getPosition());
        emp.get().setTelephone(updatedEmp.getTelephone());
        emp.get().setStatus(updatedEmp.getStatus());

        return employeeRepository.save(emp.get()).getId();

    }

    @PostMapping("/login")
    public Object login(@RequestBody Login login) throws ResourceNotFoundException {
        try {
            Long id = Long.parseLong(login.getTelephone());
            Optional<Employee> emp = employeeRepository.findById(id);
            if(!emp.isPresent()){
                throw new ResourceNotFoundException("Employee Not Found");
            }
            if(emp.get().getPassword().equals(login.getPassword())){
                return emp.get();
            }
            return -1;
        }
        catch (Exception e){
            return e.toString();
        }
    }
}
