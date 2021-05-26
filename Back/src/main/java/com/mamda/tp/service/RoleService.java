package com.mamda.tp.service;

import java.util.List;

import com.mamda.tp.model.Role;

public interface RoleService {
    public Role createRoleifnotexist(String roleName);

    public List<Role> getAllRoles();
}