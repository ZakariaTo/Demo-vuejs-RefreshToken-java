package com.mamda.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mamda.tp.model.Role;
import com.mamda.tp.model.TPUser;
import com.mamda.tp.repositories.TPUserRpos;

import com.mamda.tp.security.AppProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommandeLineStarter implements CommandLineRunner {
    @Autowired
    private TPUserRpos userRepos;
    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;
//    @Autowired
//    private AppProperties appProperties;
//
//    private Log logger = LogFactory.getLog(CommandeLineStarter.class);

    @Override
    public void run(String... args) throws Exception {
        // TPUser user = new TPUser();
        // user.setId(new Integer(1));
        // user.setEmail("zakaria.tourabii@gmail.com");
        // user.setPassword(bcryptPasswordEncoder.encode("123456"));
        // List<Role> roles = new ArrayList<>();
        // user.setRoles(roles);
        // userRepos.save(user);
        // System.out.println("User Created");
    }

}