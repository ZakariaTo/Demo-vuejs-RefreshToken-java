package com.mamda.tp.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
    private String name;
    private String adresse;
    private String cin;
}