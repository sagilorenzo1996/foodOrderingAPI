package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.Customer;
import com.finalYearProject.foodOrderingAPI.domain.Login;
import com.finalYearProject.foodOrderingAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/")
    public Long addCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer).getId();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer is not resgistered");
        }
        return customer;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Customer is not resgistered");
        }
    }


    @PutMapping("/{id}")
    public Long updateCustomer(@PathVariable Long id,@RequestBody Customer updateCustomer)throws ResourceNotFoundException{
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Customer is not resgistered");
        }
        customer.get().setAddressLineOne(updateCustomer.getAddressLineOne());
        customer.get().setAddressLineTwo(updateCustomer.getAddressLineTwo());
        customer.get().setCardExpDate(updateCustomer.getCardExpDate());
        customer.get().setCardNumber(updateCustomer.getCardNumber());
        customer.get().setCity(updateCustomer.getCity());
        customer.get().setEmail(updateCustomer.getEmail());
        customer.get().setFirstName(updateCustomer.getFirstName());
        customer.get().setLastName(updateCustomer.getLastName());
        customer.get().setPassword(updateCustomer.getPassword());
        customer.get().setStatus(updateCustomer.getStatus());
        customer.get().setTelephone(updateCustomer.getTelephone());
        return customerRepository.save(customer.get()).getId();
    }

    @PostMapping("/login")
    public Object login(@RequestBody Login login) throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findByTelephone(login.getTelephone());
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Customer Not Registered");
        }
        if(customer.get().getPassword().equals(login.getPassword())){
            return customer.get();
        }
        return -1;
    }

}
