package com.mamda.tp.repositories;

import com.mamda.tp.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepos extends JpaRepository<Client, Integer> {

}