package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByRole(String role);
}
