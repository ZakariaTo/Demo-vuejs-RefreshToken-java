package com.mamda.tp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.mamda.tp.exceptions.UserAlreadyTaken;
import com.mamda.tp.model.Permission;
import com.mamda.tp.model.Role;
import com.mamda.tp.model.TPUser;
import com.mamda.tp.repositories.RoleRepos;
import com.mamda.tp.repositories.TPUserRpos;
import com.mamda.tp.requestmodels.SignUpRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImp implements UserService {

    private final Log logger = LogFactory.getLog(UserServiceImp.class);
    private final TPUserRpos userRepos;
    private final RoleRepos roleRepos;
    private final PasswordEncoder bcryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<TPUser> userOptional = userRepos.findByEmail(email);
        TPUser user = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            List<Role> roles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                Optional<Role> dataRole = roleRepos.findByRoleName(role.getRoleName());
                roles.add(dataRole.get());
            }
            return new User(user.getEmail(),user.getPassword(),getAuthorities(roles));
        } else
            throw new RuntimeException("No user with the given Email");
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }


    private List<SimpleGrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : permissions) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add(item.getPermissionName());
        }
        return permissions;
    }

    @Override
    public TPUser getUser(String email) {
        Optional<TPUser> userOptional = userRepos.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("No user with the given Email");
    }

    @Override
    public TPUser getUser(Integer id) {
        Optional<TPUser> userOptional = userRepos.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("No user with the given id");
    }

//    private List<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        }
//        return authorities;
//
//    }

    @Override
    public TPUser createUser(SignUpRequest userRequest) throws UserAlreadyTaken {

        if (userRepos.findByEmail(userRequest.getEmail()).isPresent())
            throw new UserAlreadyTaken("user with email: " + userRequest.getEmail() + " is already taken");

        TPUser user = new TPUser(userRequest.getEmail().trim(),
                bcryptPasswordEncoder.encode(userRequest.getPassword()));
        List<Role> roles = new ArrayList<>();
        if (userRequest.getRolesId().size() > 0) {
            for (Integer id : userRequest.getRolesId()) {
                Optional<Role> roleOpt = roleRepos.findById(id);
                if (roleOpt.isPresent())
                    roles.add(roleOpt.get());
            }
        }
        user.setRoles(roles);

        return userRepos.save(user);
    }

    @Override
    public List<TPUser> getAllUsers() {
        return userRepos.findAll();
    }

    @Override
    public TPUser updateUser(Integer id, SignUpRequest userRequest) throws UserAlreadyTaken {
        Optional<TPUser> userOpt = userRepos.findById(id);
        if (userRequest.getEmail() == null)
            throw new RuntimeException("You must provide a valid email");
        if (id == null)
            throw new RuntimeException("you must provide a valid id");
        if (!userOpt.isPresent())
            throw new UsernameNotFoundException("user not foun with id: " + id);

        Optional<TPUser> userExists = userRepos.findByEmail(userRequest.getEmail());
        if (userExists.isPresent() && !userOpt.get().getId().equals(userExists.get().getId()))
            throw new UserAlreadyTaken("user with email : " + userRequest.getEmail() + " is already exists");

        TPUser userEntity = userOpt.get();
        userEntity.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null)
            userEntity.setPassword(bcryptPasswordEncoder.encode(userRequest.getPassword()));
        List<Role> updatedRoles = new ArrayList<>();
        if (userRequest.getRolesId() != null && userRequest.getRolesId().size() > 0) {
            for (Integer idRole : userRequest.getRolesId()) {
                Optional<Role> roleOpt = roleRepos.findById(idRole);
                if (roleOpt.isPresent())
                    updatedRoles.add(roleOpt.get());
            }
        }
        userEntity.setRoles(updatedRoles);

        return userRepos.save(userEntity);
    }

    @Override
    public void deletUser(Integer id) {
        Optional<TPUser> userOpt = userRepos.findById(id);
        if (userOpt.isPresent()) {
            TPUser user = userOpt.get();
            userRepos.delete(user);
        } else {
            throw new RuntimeException("There is No user with the given id :" + id);
        }
    }

    @Override
    public void uploadAvatar(Integer userID, String originalFileNAme) {
        Optional<TPUser> userOpt = userRepos.findById(userID);
        if (!userOpt.isPresent())
            throw new RuntimeException("No user exist with the given id: " + userID);
        TPUser user = userOpt.get();
        user.setAvatar(originalFileNAme);
        userRepos.save(user);
    }

}