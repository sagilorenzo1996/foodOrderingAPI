package com.finalYearProject.foodOrderingAPI.repository;

import com.finalYearProject.foodOrderingAPI.domain.Item;
import com.finalYearProject.foodOrderingAPI.domain.LineItem;
import org.springframework.data.repository.CrudRepository;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {
    Iterable<LineItem> findAllByOrderId(Long id);
}
