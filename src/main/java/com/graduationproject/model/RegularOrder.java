package com.graduationproject.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "regular_order")
public class RegularOrder implements Serializable {

    @Id
//    @ManyToMany
    @JoinColumn(table = "prescription", name = "id")
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String prescription_id;

//    @ManyToMany
    @JoinColumn(table = "patient", name = "id")
//    @GeneratedValue(generator = "UUID2")
//    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String patient_id;

    @Id
    @Column(columnDefinition = "BIGINT(20)", nullable = false)
    private long fire_time;



    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public long getFire_time() {
        return fire_time;
    }

    public void setFire_time(long fire_time) {
        this.fire_time = fire_time;
    }
}
