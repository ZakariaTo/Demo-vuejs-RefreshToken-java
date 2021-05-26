package com.mamda.tp.requestmodels;

import java.util.List;

import com.mamda.tp.model.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentUserResponse {
    private Integer id;
    private String email;
    private List<Role> roles;
    private String avatar;

    public CurrentUserResponse(Integer id, String email, List<Role> roles, String avatar) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.avatar = avatar;
    }
}