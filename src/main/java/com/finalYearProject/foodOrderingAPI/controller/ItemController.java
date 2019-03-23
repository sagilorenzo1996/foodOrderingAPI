package com.finalYearProject.foodOrderingAPI.controller;

import com.finalYearProject.foodOrderingAPI.domain.Category;
import com.finalYearProject.foodOrderingAPI.domain.Item;
import com.finalYearProject.foodOrderingAPI.repository.CategoryRepository;
import com.finalYearProject.foodOrderingAPI.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {
    private String itemAvailable = "Y";
    private String itemUnAvailable = "N";

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/all")
    public Iterable<Item> getAllItems() {
        Iterable<Item> items = itemRepository.findAll();
        return items;
    }

    @PostMapping("/")
    public Long addItem(@Valid @RequestBody Item item) {
        return itemRepository.save(item).getId();
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()){
            throw new ResourceNotFoundException("Item is not in menu");
        }
        else {
            itemRepository.delete(item.get());
        }
    }

    @PutMapping("/{id}")
    public Long updateItem(@PathVariable Long id,@RequestBody Item updatedItem) throws ResourceNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()){
            throw new ResourceNotFoundException("Item is not in menu");
        }
        else {
            item.get().setDescription(updatedItem.getDescription());
            item.get().setName(updatedItem.getName());
            item.get().setPrice(updatedItem.getPrice());
            item.get().setType(updatedItem.getType());
        }
        return itemRepository.save(item.get()).getId();
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
    public String makeAvailable(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Item is not in menu");
        }
        item.get().setAvailability(this.itemAvailable);
        return "Item " + item.get().getName() + " made available";
    }

    @GetMapping("/makeUnAvailable/{id}")
    public String makeUnAvailable(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Item is not in menu");
        }
        item.get().setAvailability(this.itemUnAvailable);
        return "Item " + item.get().getName() + " made un-available";
    }

    @GetMapping("/category/{status}")
    public Iterable<Category> getCategories(@PathVariable String status){
        return categoryRepository.findAllByStatus(status);
    }

    @PostMapping("/category")
    public Long createCategory(@RequestBody Category category){
        return categoryRepository.save(category).getId();
    }

    @GetMapping("/{category}")
    public Iterable<Item> getItems(@PathVariable String category){
        return itemRepository.findAllByType(category);
    }

    @GetMapping("/category/status/{id}")
    public String updateCategoryStatus(@PathVariable Long id,@RequestParam String status) throws ResourceNotFoundException{
        Optional<Category> category= categoryRepository.findById(id);
        if(!category.isPresent()){
            throw new ResourceNotFoundException("Category Not found");
        }
        category.get().setStatus(status);
        return category.get().getCategoryName()+" set to "+status;
    }

}
