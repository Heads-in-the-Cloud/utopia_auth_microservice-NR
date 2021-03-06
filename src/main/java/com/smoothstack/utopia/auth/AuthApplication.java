package com.smoothstack.utopia.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
    //    CommandLineRunner run(UserService userService) {
    //        return args -> {
    //            userService.saveUser(new User("admin", "12345", null));
    //
    //            userService.addRoleToUser("admin", "ROLE_ADMIN");
    //        };
    //    }
}
