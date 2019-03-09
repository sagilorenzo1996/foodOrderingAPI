package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.Customer;
import com.finalYearProject.foodOrderingAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer)
    {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
        if(existingCustomer.isPresent()){
            return new ResponseEntity<>("Customer already registered", HttpStatus.BAD_REQUEST);
        }
        else {
            customerRepository.save(customer);
            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable Long id )throws ResourceNotFoundException{
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Customer is not resgistered");
        }
        return customer;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            customerRepository.deleteById(id);
        }
        else {
            throw  new ResourceNotFoundException("Customer is not resgistered");
        }
    }

}
