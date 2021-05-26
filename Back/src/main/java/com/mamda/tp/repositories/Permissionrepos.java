package com.mamda.tp.repositories;

import com.mamda.tp.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Permissionrepos extends JpaRepository<Permission,Integer> {
}
