package com.aliyev.yerassyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "password")
    private String password;

    public User() {

    }

    public User(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }



    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "User[id=" + id + ", name=" + name + ", email=" + email  + "]";
    }
}