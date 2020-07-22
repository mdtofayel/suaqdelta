package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Role;
import com.happyride.eservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> getRoleByRole(String role) {
        return roleRepository.getRoleByRole(role);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
