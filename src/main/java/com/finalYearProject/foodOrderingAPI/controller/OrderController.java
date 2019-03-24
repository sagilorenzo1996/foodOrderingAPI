package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.*;
import com.finalYearProject.foodOrderingAPI.repository.*;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private String AVAILABLE = "available";
    private String UNAVAILABLE  = "unavailable";
    private String DEFAULT_PROMO  = "default";
    private String DEFAULT_TYPE  = "default";
    private String WAITING  = "waiting";
    private String PLACED  = "placed";
    private String COMPLETED = "completed";
    private String DELIVERED = "delivered";
    private String INPROGRESS = "inprogress";
    private String ALL = "all";

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
    @Transactional
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
        order.setCustomerId(customer.get().getId());
        order.setEstimatedDeliveryDate(new Date());
        order.setDriverId(driver.get().getId());
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
            lineItem.setOrderId(placedOrder.getId());
            lineItem.setQuantity(item.getQuantity());
            lineItem.setStatus(this.PLACED);
            lineItem.setPrice(BigDecimal.valueOf(item.getPrice()));
            lineItemRepository.save(lineItem);
        });
        return orderRepository.save(placedOrder).getId();
    }

    @GetMapping("/getByStatus/{status}")
    public Iterable<Order> getOrdersByStatus(@PathVariable String status) {
        if (status.equals(this.WAITING)) {
            return orderRepository.findAllByStatus(this.WAITING);
        }
        if (status.equals(this.INPROGRESS)) {
            return orderRepository.findAllByStatus(this.INPROGRESS);
        }
        if (status.equals(this.COMPLETED)) {
            return orderRepository.findAllByStatus(this.COMPLETED);
        }
        return orderRepository.findAllByStatus(this.DELIVERED);
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()){
            throw new ResourceNotFoundException("order not found");
        }
        return order;
    }

    @GetMapping("/assign")
    @Transactional
    public String assignOrder(@RequestParam Long order,@RequestParam Long driver,@RequestParam Long emp)throws ResourceNotFoundException{
        Optional<Order> orderObj = orderRepository.findById(order);
        Optional<Employee> empObj = employeeRepository.findById(emp);
        Optional<Driver> driverObj = driverRepository.findById(driver);
        if(!orderObj.isPresent() || !empObj.isPresent() || !driverObj.isPresent()){
            throw new ResourceNotFoundException("Cant process order");
        }
        orderObj.get().setDriverId(driverObj.get().getId());
        Iterable<LineItem> lineItems = lineItemRepository.findAllByOrderId(order);
        lineItems.forEach(item->{
            item.setEmployeeId(emp);
            lineItemRepository.save(item);
        });
        return "Order Asigned";
    }

    @GetMapping("/lineItem/{id}")
    public String changeLineItem(@PathVariable Long id,@RequestParam String status) throws ResourceNotFoundException{
        Optional<LineItem> lineItem = lineItemRepository.findById(id);
        if(!lineItem.isPresent()){
            throw new ResourceNotFoundException("Line Item Not Found");
        }
        lineItem.get().setStatus(status);
        lineItemRepository.save(lineItem.get());
        return "Items status set to "+status;
    }

    @GetMapping("/setStatus/{id}")
    public String changeOrderStatus(@PathVariable Long id,@RequestParam String status) throws ResourceNotFoundException{
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()){
            throw new ResourceNotFoundException("Line Item Not Found");
        }
        order.get().setStatus(status);
        orderRepository.save(order.get());
        return "order status set to "+status;
    }
}
