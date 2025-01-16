package com.example.loginauthapi.services;

import com.example.loginauthapi.domain.role.AppRole;
import com.example.loginauthapi.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Arrays;

@Service
public class RoleInitializer {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initializeRoles() {
        String[] roles = {"ROLE_USER", "ROLE_ADM"};

        Arrays.stream(roles).forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                AppRole role = new AppRole(roleName);
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Role " + roleName + " created.");
            } else {
                System.out.println("Role " + roleName + " already exists.");
            }
        });
    }
}
