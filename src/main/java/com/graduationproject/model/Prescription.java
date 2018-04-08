package com.graduationproject.model;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "prescription")
@DynamicInsert
public class Prescription implements Serializable {


    @Id
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date prescription_date;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double price;

    //TODO: Declaration of history table
//    @OneToOne
//    @JoinColumn(table = "history", name = "id")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false) //TODO: history_id is nullable or not?!!
    @ColumnDefault("1")
    private String history_id;

//    @ManyToOne
    @JoinColumn(table = "patient", name = "id")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private String patient_id;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPrescription_date() {
        return prescription_date;
    }

    public void setPrescription_date(Date prescription_date) {
        this.prescription_date = prescription_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String  getHistory_id() {
        return history_id;
    }

    public void setHistory_id(String history_id) {
        this.history_id = history_id;
    }

    public String getUser_id() {
        return patient_id;
    }

    public void setUser_id(String patient_id) {
        this.patient_id = patient_id;
    }
}
