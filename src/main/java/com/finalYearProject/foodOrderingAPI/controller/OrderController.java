package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.*;
import com.finalYearProject.foodOrderingAPI.repository.*;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
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
    private String PLACED  = "placed";

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

    @PostMapping("/initOrder/{id}")
    public Long initiateOrder(@PathVariable Long id,@Valid @RequestBody LineItemRequestBody lineItemList,@RequestParam String promo,@RequestParam String type)throws ResourceNotFoundException,IllegalStateException {
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isPresent()){
            throw new ResourceNotFoundException("Cannot find customer");
        }
        Long defId = new Long(1);
        Optional<Employee> employee = employeeRepository.findById(defId);
        if(!employee.isPresent()){
            throw new ResourceNotFoundException("Cannot find Employee");
        }
        Optional<Driver> driver = driverRepository.findById(defId);
        if(!driver.isPresent()){
            throw new ResourceNotFoundException("Cannot find Driver");
        }
        Order order = new Order();
        order.setCustomer(customer.get());
        order.setEstimatedDeliveryDate(new Date());
        order.setDriver(driver.get());
        order.setLocation(customer.get().getAddressLineOne()+","+customer.get().getAddressLineTwo()+","+customer.get().getCity());
        if(!type.isEmpty()){
            order.setType(type);
        }
        else{
            order.setType(this.DEFAULT_TYPE);
        }
        order.setStatus(this.WAITING);
        order.setOrderedDate(new Date());
        if(Iterables.size(lineItemList.getLineItem())<1){
            throw new IllegalStateException("No items In order");
        }
        if(!promo.isEmpty()){
            order.setPromo(promo);
        }
        else{
            order.setPromo(this.DEFAULT_PROMO);
        }
        BigDecimal discount = new BigDecimal(0.00);
        order.setTotal(lineItemList.getTotal());
        Order placedOrder = orderRepository.save(order);
        lineItemList.getLineItem().forEach(item -> {
            LineItem lineItem = new LineItem();
            Optional<Item> detailedItem = itemRepository.findById(item.getId());
            if(!detailedItem.isPresent()){
                throw new ResourceNotFoundException("Item Not Found");
            }
            lineItem.setEmployeeId(employee.get().getId());
            lineItem.setItemId(detailedItem.get().getId());
            lineItem.setOrder(placedOrder);
            lineItem.setQuantity(item.getQuantity());
            lineItem.setStatus(this.PLACED);
            lineItem.setPrice(BigDecimal.valueOf(item.getPrice()));
            lineItemRepository.save(lineItem);
        });
        return orderRepository.save(placedOrder).getId();
    }



}
