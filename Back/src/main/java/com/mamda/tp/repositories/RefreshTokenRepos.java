package com.mamda.tp.repositories;

import com.mamda.tp.model.RefreshToken;
import com.mamda.tp.model.TPUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepos extends JpaRepository<RefreshToken, Integer> {
    public Optional<RefreshToken> findByToken(String token);

    public Optional<RefreshToken> findByUser(TPUser user);
}
