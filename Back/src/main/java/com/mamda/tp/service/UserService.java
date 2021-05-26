package com.mamda.tp.service;

import java.util.List;

import com.mamda.tp.exceptions.UserAlreadyTaken;
import com.mamda.tp.model.TPUser;
import com.mamda.tp.requestmodels.SignUpRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public TPUser getUser(String email);

    public TPUser getUser(Integer id);

    public TPUser createUser(SignUpRequest userRequest) throws UserAlreadyTaken;

    public List<TPUser> getAllUsers();

    public TPUser updateUser(Integer id, SignUpRequest userRequest) throws UserAlreadyTaken;

    public void deletUser(Integer id);

    public void uploadAvatar(Integer userID, String originalFileNAme);
}