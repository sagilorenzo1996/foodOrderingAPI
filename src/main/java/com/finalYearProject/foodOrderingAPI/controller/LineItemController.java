package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.repository.ItemRepository;
import com.finalYearProject.foodOrderingAPI.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lineItem")
public class LineItemController {

    @Autowired
    LineItemRepository lineItemRepository;

    @Autowired
    ItemRepository itemRepository;

}
