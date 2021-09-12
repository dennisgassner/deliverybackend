package org.dennis.deliverybackend.controller;

import org.dennis.deliverybackend.model.Pizza;
import org.dennis.deliverybackend.model.PizzaListItem;
import org.dennis.deliverybackend.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DeliveryController {

    @Autowired
    PizzaRepository repository;

    @GetMapping("/delivery/pizza")
    public List<PizzaListItem> getAllPizzas() {
        return repository.getList().stream().map(p -> new PizzaListItem().setId(p.getId()))
                .map(pi -> pi.add(linkTo(methodOn(DeliveryController.class).getPizza(pi.getId())).withRel("pizzaDetails")))
                .collect(Collectors.toList());
    }

    @GetMapping("/delivery/pizza/{id}")
    public Pizza getPizza(@PathVariable int id) {
        return repository.getPizza(id);
    }

}
