package com.finalYearProject.foodOrderingAPI.repository;

import com.finalYearProject.foodOrderingAPI.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Iterable<Employee> findAllByStatus(String status);
    Optional<Employee> findByPosition(String position);
}
