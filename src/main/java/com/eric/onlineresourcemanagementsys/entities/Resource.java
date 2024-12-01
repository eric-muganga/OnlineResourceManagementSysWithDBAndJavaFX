package com.eric.onlineresourcemanagementsys.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "resources")
public abstract class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String name;
    protected String username;
    protected String password;


    public Resource() {}
    public Resource(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void displayInfo();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
