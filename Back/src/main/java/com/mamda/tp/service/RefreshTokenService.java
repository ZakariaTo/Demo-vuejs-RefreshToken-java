package com.mamda.tp.service;

import com.mamda.tp.model.RefreshToken;
import com.mamda.tp.model.TPUser;
import com.mamda.tp.repositories.RefreshTokenRepos;
import com.mamda.tp.repositories.TPUserRpos;
import com.mamda.tp.security.SecurityCanstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
/**
 * author Zakaria Tourabi
 * This class is for generating the busniss logic of RefreshToken data
 */
public class RefreshTokenService {

    private final RefreshTokenRepos refreshTokenRepos;
    private final TPUserRpos userRepos;
    private Log logger = LogFactory.getLog(RefreshTokenService.class);

    public Optional<RefreshToken> findByToken(String token){
        return this.refreshTokenRepos.findByToken(token);
    }

    /**
     * This function get our user's email connected and generate a Refreshtoken and link it to that user if it is not presented in the database or if it's expired, otherwise, get
     * the refreshtoken for the given user and return it
     * @param  userEmail
     * @return RefreshToekn valid object
     */
    public RefreshToken generateRefreshTokenIfnotExistOrIsExpired(String userEmail){

        TPUser user = this.userRepos.findByEmail(userEmail).get();
        Optional<RefreshToken> refreshTokenOpt = this.refreshTokenRepos.findByUser(user);
        if(refreshTokenOpt.isPresent()){
            try {
                RefreshToken token = verifyExpiration(refreshTokenOpt.get());
                return token;
            }catch (Exception e){
                this.logger.info("a new Refresh Token will be generated");
            }
        }
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(SecurityCanstants.REFRESHTOKEN_EXPIRATION_TIME));
        return this.refreshTokenRepos.save(refreshToken);

    }

    /**
     * this function for validating that a refreshToken passed in the parameters is not expired, if it s the case, that s mean expired, it deleted
     * from the database otherwise it return the passed token
     * @param token
     * @return
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            this.refreshTokenRepos.delete(token);
            throw new RuntimeException("Refresh token was expired.");
        }
        return token;
    }
}
