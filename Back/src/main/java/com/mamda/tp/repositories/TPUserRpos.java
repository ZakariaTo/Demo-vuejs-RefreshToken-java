package com.mamda.tp.repositories;

import java.util.Optional;

import com.mamda.tp.model.TPUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TPUserRpos extends JpaRepository<TPUser, Integer> {
    public Optional<TPUser> findByEmail(String email);
}