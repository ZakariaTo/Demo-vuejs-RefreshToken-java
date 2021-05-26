package com.mamda.tp.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenResponse {

    private String refreshToken;
    private String token;

}
