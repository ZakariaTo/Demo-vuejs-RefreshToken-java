package com.mamda.tp.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mamda.tp.exceptions.UserAlreadyTaken;
import com.mamda.tp.model.TPUser;
import com.mamda.tp.requestmodels.CurrentUserResponse;
import com.mamda.tp.requestmodels.SignUpRequest;
import com.mamda.tp.security.CurrentUser;
import com.mamda.tp.service.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/private/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${dirimages}")
    private String dirImages;

//    @GetMapping("/current")
//    public CurrentUserResponse getCurrent(@CurrentUser TPUser user) {
//        System.out.println("user value in current Request: "+user);
//        CurrentUserResponse userResponse = new CurrentUserResponse(user.getId(), user.getEmail(), user.getRoles(),
//                user.getAvatar());
//        return userResponse;
//    }
    @GetMapping("/current")
    public ResponseEntity<Object> getCurrent(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TPUser currentUser = this.userService.getUser(auth.getName());
        if(currentUser == null)
            return new ResponseEntity<Object>("NO Current User Found", HttpStatus.FORBIDDEN);
        return new ResponseEntity<Object>(currentUser, HttpStatus.OK);
    }

    @PostMapping("/")
    public TPUser addUser(@RequestBody SignUpRequest signupRequest) throws UserAlreadyTaken {
        return userService.createUser(signupRequest);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public List<TPUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public TPUser updateUser(@PathVariable Integer id, @RequestBody SignUpRequest signUpRequest)
            throws UserAlreadyTaken {
        return userService.updateUser(id, signUpRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deletUser(id);
    }

    @PostMapping("/uploadavatar")
    public boolean uploadpicture(@RequestBody MultipartFile file)
            throws IllegalStateException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TPUser currentUser = this.userService.getUser(auth.getName());
        if (!(file.isEmpty()) && currentUser!=null) {
            userService.uploadAvatar(currentUser.getId(), file.getOriginalFilename());
            file.transferTo(new File(dirImages + file.getOriginalFilename()));
            return true;
        }
        return false;
    }

}