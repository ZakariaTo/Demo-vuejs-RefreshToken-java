package com.mamda.tp.repositories;

import java.util.Optional;

import com.mamda.tp.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepos extends JpaRepository<Role, Integer> {
    public Optional<Role> findByRoleName(String roleName);
}