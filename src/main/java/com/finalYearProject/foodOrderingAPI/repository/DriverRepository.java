package com.finalYearProject.foodOrderingAPI.repository;

import com.finalYearProject.foodOrderingAPI.domain.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Iterable<Driver> findAllByStatus(String status);
}
