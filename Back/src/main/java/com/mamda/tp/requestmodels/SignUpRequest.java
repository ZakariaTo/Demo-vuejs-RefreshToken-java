package com.mamda.tp.requestmodels;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private List<Integer> rolesId;
}