package com.mamda.tp.security;

import com.mamda.tp.model.TPUser;
import com.mamda.tp.repositories.TPUserRpos;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTProvider {


    private final AppProperties appProperties;

    private final TPUserRpos userRpos;

    public String generateToken(String subject){
        Optional<TPUser> tpUserOpt = userRpos.findByEmail(subject);
        if(!tpUserOpt.isPresent()){
            return "";
        }
        String token = Jwts.builder().setSubject(Integer.toString(tpUserOpt.get().getId()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityCanstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, appProperties.getSecretToken()).compact();
        return token;
    }

    public Integer getIdFromJWTToken(String token){
        token = token.replace(SecurityCanstants.TOKEN_PREFIX, "");
        String id = Jwts.parser().setSigningKey(appProperties.getSecretToken()).parseClaimsJws(token)
                .getBody().getSubject();
        return Integer.parseInt(id);
    }

    public boolean validateJwtToken(String authToken) throws ExpiredJwtException{
        authToken = authToken.replace(SecurityCanstants.TOKEN_PREFIX, "");
        try {
            Jwts.parser().setSigningKey(appProperties.getSecretToken()).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        }
    }
}
