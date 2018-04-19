package com.graduationproject.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cart_medicine")
public class CartMedicine implements Serializable {

    @Id
//    @ManyToMany
    @JoinColumn(table = "medicine", name = "id")
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String medicine_id;

    @Id
//    @ManyToMany
    @JoinColumn(table = "prescription", name = "id")
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String prescription_id;

    @Column(columnDefinition = "INTEGER(11)", nullable = false)
    private int quantity;

    @Column(columnDefinition = "INTEGER(11)", nullable = false)
    private int repeat_duration;



    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRepeat_duration() {
        return repeat_duration;
    }

    public void setRepeat_duration(int repeat_duration) {
        this.repeat_duration = repeat_duration;
    }
}
