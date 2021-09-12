package org.dennis.deliverybackend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

public class PizzaListItem extends RepresentationModel<PizzaListItem> {

    private int id;

    public int getId() {
        return id;
    }

    public PizzaListItem setId(int id) {
        this.id = id;
        return this;
    }

}
