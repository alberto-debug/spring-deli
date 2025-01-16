package com.example.loginauthapi.domain.role;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public AppRole(){

    }
    // Constructor with name argument
    public AppRole(String name) {
        this.name = name;
    }


}
