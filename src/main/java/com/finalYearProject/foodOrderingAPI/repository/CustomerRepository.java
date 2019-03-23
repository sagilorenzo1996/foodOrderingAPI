package com.finalYearProject.foodOrderingAPI.repository;

import com.finalYearProject.foodOrderingAPI.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByTelephone(String telephone);
}
