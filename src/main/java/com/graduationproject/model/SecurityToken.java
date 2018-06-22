package com.graduationproject.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "security_token")
public class SecurityToken {

    @Id
    @Column(columnDefinition = "TEXT", unique=true, nullable=false)
    private String username;

    @Column(columnDefinition = "TEXT", unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_used;

    public SecurityToken(String username, String token, Date last_used) {
        this.username = username;
        this.token = token;
        this.last_used = last_used;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLast_used() {
        return last_used;
    }

    public void setLast_used(Date last_used) {
        this.last_used = last_used;
    }
}
