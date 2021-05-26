package com.mamda.tp.web;

import java.util.List;

import com.mamda.tp.model.Role;
import com.mamda.tp.requestmodels.RoleRequest;
import com.mamda.tp.service.RoleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/private/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/")
    public Role createRole(@RequestBody RoleRequest roleRequest) {
        return roleService.createRoleifnotexist(roleRequest.getRoleName());
    }

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

}