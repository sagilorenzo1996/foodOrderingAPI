package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.Item;
import com.finalYearProject.foodOrderingAPI.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {
    private String itemAvailable = "Y";
    private String itemUnAvailable = "N";

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/")
    public Iterable<Item> getAllItems() {
        Iterable<Item> items = itemRepository.findAll();
        return items;
    }

    @GetMapping("/{id}")
    public Optional<Item> getItem(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Item is not in menu");
        }
        return item;
    }

    @GetMapping("/makeAvailable/{id}")
    public String makeAvailable(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Item>  item = itemRepository.findById(id);
        if (!item.isPresent()){
            throw new ResourceNotFoundException("Item is not in menu");
        }
        item.get().setAvailability(this.itemAvailable);
        return "Item "+item.get().getName()+" made available";
    }

    @GetMapping("/makeAvailable/{id}")
    public String makeUnAvailable(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Item>  item = itemRepository.findById(id);
        if (!item.isPresent()){
            throw new ResourceNotFoundException("Item is not in menu");
        }
        item.get().setAvailability(this.itemUnAvailable);
        return "Item "+item.get().getName()+" made un-available";
    }
    
}
