package com.graduationproject.model;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "medicine")
public class Medicine {


    @Id
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String form;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String active_ingredients;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double price;

    @Column(columnDefinition = "INTEGER(11)", nullable = false)
    private int quantity;

    @Column(columnDefinition = "INTEGER(1)", nullable = false)
    @ColumnDefault("0")
    private int isRestricted;

    @Column(columnDefinition = "INTEGER(2)")
    @ColumnDefault("0")
    private int z;

    @Column(columnDefinition = "INTEGER(2)")
    @ColumnDefault("0")
    private int x;

    @Column(columnDefinition = "INTEGER(2)")
    @ColumnDefault("0")
    private int y;

    //getters;setters;

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

    public int getIsRestricted() {
        return isRestricted;
    }

    public void setIsRestricted(int isRestricted) {
        this.isRestricted = isRestricted;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
