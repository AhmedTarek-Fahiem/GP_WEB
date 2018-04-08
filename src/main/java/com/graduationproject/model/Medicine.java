package com.graduationproject.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "medicine")
public class Medicine {


    @Id
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String category;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String form;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String active_ingredients;

    @Column(columnDefinition = "DOUBLE", nullable = false, unique = true)
    private double price;

    @Column(columnDefinition = "INTEGER(11)", nullable = false, unique = true)
    private int quantity;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getActive_ingredients() {
        return active_ingredients;
    }

    public void setActive_ingredients(String active_ingredients) {
        this.active_ingredients = active_ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
