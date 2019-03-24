package com.finalYearProject.foodOrderingAPI.repository;

import com.finalYearProject.foodOrderingAPI.domain.Order;
import org.springframework.data.repository.CrudRepository;



public interface OrderRepository extends CrudRepository <Order, Long> {
    Iterable<Order> findAllByStatus(String status);

}
