package org.dennis.deliverybackend.controller;

import org.dennis.deliverybackend.model.Pizza;
import org.dennis.deliverybackend.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryController {

    @Autowired
    PizzaRepository repository;



    @GetMapping("/delivery/list_all")
    public List<Pizza> getAllPizzas() {
        return repository.getAll();
    }

}
