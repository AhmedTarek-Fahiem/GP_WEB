package com.graduationproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "history")
public class History {

    @Id
    @Column(columnDefinition = "VARCHAR(36)", updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
