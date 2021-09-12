package org.dennis.deliverybackend.repository;


import com.mongodb.client.MongoClients;
import org.bson.types.ObjectId;
import org.dennis.deliverybackend.model.Pizza;
import org.dennis.deliverybackend.model.PizzaListItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class PizzaRepository {

    private List<Pizza> items = new LinkedList<Pizza>();

    private MongoOperations mongoOps;

    @Value( "${spring.mongouri}" )
    private String mongodbUri;

    private MongoOperations getMongoOps() {
        if(mongoOps == null) {
            mongoOps = new MongoTemplate(MongoClients.create(mongodbUri), "admin");
        }
        return mongoOps;
    }

    public List<Pizza> getList() {
        if (items.size() <= 0) {
            getMongoOps().findAll(Pizza.class, "pizzas").stream().forEach(pizza -> items.add(pizza));
        }
        return items;
    }

    public Pizza getPizza(int id) {
        return getMongoOps().findOne(new Query(Criteria.where("id").is(id)), Pizza.class, "pizzas");
    }

}
