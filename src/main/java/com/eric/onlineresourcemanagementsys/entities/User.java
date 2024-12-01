package com.eric.onlineresourcemanagementsys.entities;

import com.eric.onlineresourcemanagementsys.utils.EncryptionUtil;
import jakarta.persistence.*;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private byte[] encryptedPassword;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Resource> resources = new ArrayList<>();


    public User() {

    }

    public User(String username, String password, SecretKey key) {
        this.username = username;
        this.encryptedPassword = EncryptionUtil.encryptPassword(password,key);
    }

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public boolean verifyPassword(String inputPassword, SecretKey key) {
        return EncryptionUtil.verifyPassword(encryptedPassword, inputPassword, key);
    }
}
