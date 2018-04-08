package com.graduationproject.model;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity()
@Table(name = "patient")
@DynamicInsert
public class Patient {


    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.uuid.standardrandomstrategy")
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(columnDefinition = "VARCHAR(15)", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
//    @Temporal(TemporalType.DATE)
    private long dob;

    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    private char gender;

    @Column(columnDefinition = "VARCHAR(8)")
    @ColumnDefault("0")
    private String PIN;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }
}
