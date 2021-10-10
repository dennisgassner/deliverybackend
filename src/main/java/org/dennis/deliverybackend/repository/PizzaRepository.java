package org.dennis.deliverybackend.repository;

import org.bson.types.ObjectId;
import org.dennis.deliverybackend.model.Pizza;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends MongoRepository<Pizza, ObjectId> {
    Pizza findById(int id);
}
