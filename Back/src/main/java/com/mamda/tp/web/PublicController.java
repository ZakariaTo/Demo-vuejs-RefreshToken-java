package com.mamda.tp.web;

import com.mamda.tp.model.RefreshToken;
import com.mamda.tp.requestmodels.RefreshTokenRequest;
import com.mamda.tp.requestmodels.RefreshTokenResponse;
import com.mamda.tp.security.JWTProvider;
import com.mamda.tp.security.SecurityCanstants;
import com.mamda.tp.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final RefreshTokenService refreshTokenService;
    private final JWTProvider jwtProvider;
    private Log logger = LogFactory.getLog(PublicController.class);

    @PostMapping("/refreshToken")
    public ResponseEntity<?> checkRefreshToken(@RequestBody RefreshTokenRequest request){
        this.logger.info("Generating a new valid token");
        String refreshToken = request.getRefreshToken();
        if(refreshToken.trim().equals(""))
            return new ResponseEntity<String>("Refresh Token cannot be empty", HttpStatus.BAD_REQUEST);
        return this.refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newGeneratedToken = jwtProvider.generateToken(user.getEmail());
                    return ResponseEntity.ok(new RefreshTokenResponse(refreshToken, SecurityCanstants.TOKEN_PREFIX + newGeneratedToken));
                }).orElseThrow(() -> new RuntimeException("Refresh Token expired, Please Sign in"));
    }
}
