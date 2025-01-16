package com.example.loginauthapi.domain.role;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
