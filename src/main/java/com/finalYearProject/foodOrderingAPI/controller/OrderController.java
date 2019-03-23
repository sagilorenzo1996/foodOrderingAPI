package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.*;
import com.finalYearProject.foodOrderingAPI.repository.*;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private String AVAILABLE = "available";
    private String UNAVAILABLE  = "unavailable";
    private String DEFAULT_PROMO  = "default";
    private String DEFAULT_TYPE  = "default";
    private String WAITING  = "waiting";

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    LineItemRepository lineItemRepository;

    @Autowired
    EmployeeRepository employeeRepository;

//    @PostMapping("/initOrder/{id}")
//    public Long initiateOrder(@PathVariable Long id,@Valid @RequestBody Iterable<LineItemRequestBody> lineItemList,@RequestParam String promo,@RequestParam String type)throws ResourceNotFoundException {
//        Optional<Customer> customer = customerRepository.findById(id);
//        if(!customer.isPresent()){
//            throw new ResourceNotFoundException("Cannot find customer");
//        }
//        Optional<Employee> employee = employeeRepository.findById(Long.valueOf(1));
//        if(!employee.isPresent()){
//            throw new ResourceNotFoundException("Cannot find Employee");
//        }
//        Optional<Driver> driver = driverRepository.findById(Long.valueOf(1));
//        if(!driver.isPresent()){
//            throw new ResourceNotFoundException("Cannot find Driver");
//        }
//        Order order = new Order();
//        order.setCustomer(customer.get());
//        order.setEstimatedDeliveryDate(new Date());
//        order.setDriver(driver.get());
//        order.setLocation(customer.get().getAddressLineOne()+","+customer.get().getAddressLineTwo()+","+customer.get().getCity());
//        if(!promo.isEmpty()){
//            order.setPromo(promo);
//        }
//        else{
//            order.setPromo(this.DEFAULT_PROMO);
//        }
//        if(!type.isEmpty()){
//            order.setType(type);
//        }
//        else{
//            order.setType(this.DEFAULT_TYPE);
//        }
//        order.setStatus(this.WAITING);
//        order.setOrderedDate(new Date());
//        lineItemList.forEach(name -> {
//
//        });

    }


}
