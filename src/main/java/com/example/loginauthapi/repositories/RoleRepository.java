package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.role.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole,Long> {

    Optional<AppRole> findByName(String name);

}
