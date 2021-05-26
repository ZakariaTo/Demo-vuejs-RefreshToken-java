package com.mamda.tp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.mamda.tp.model.Role;
import com.mamda.tp.repositories.RoleRepos;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepos roleRepos;

    @Override
    public Role createRoleifnotexist(String roleName) {

        Optional<Role> roleOpt = roleRepos.findByRoleName(roleName.trim());
        if (!roleOpt.isPresent()) {
            Role role = new Role();
            role.setRoleName(roleName.trim());
            return roleRepos.save(role);
        }
        return roleOpt.get();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepos.findAll();
    }

}