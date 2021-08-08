package org.dennis.deliverybackend.repository;


import com.mongodb.client.MongoClients;
import org.dennis.deliverybackend.model.Pizza;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class PizzaRepository {

    private List<Pizza> items = new LinkedList<Pizza>();

    @Value( "${spring.mongouri}" )
    private String mongodbUri;

    public List<Pizza> getAll() {
        if(items.size()<=0) {
            MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongodbUri), "admin");
            for (Pizza p : mongoOps.findAll(Pizza.class, "pizzas")) {
                items.add(p);
            }
        }
        return items;
    }

}
