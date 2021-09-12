package org.dennis.deliverybackend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Pizza {

    @Id
    private ObjectId _id;
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;

    public int getId() {
        return id;
    }

    public Pizza setId(int id) {
        this.id = id;
        return this;
    }

    public Pizza setId(ObjectId id) {
        this._id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Pizza setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Pizza setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Pizza setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Pizza setImage(String image) {
        this.image = image;
        return this;
    }

}
