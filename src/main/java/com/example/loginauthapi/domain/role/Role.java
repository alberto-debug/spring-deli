package com.example.loginauthapi.domain.role;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
