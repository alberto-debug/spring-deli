package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);

}
