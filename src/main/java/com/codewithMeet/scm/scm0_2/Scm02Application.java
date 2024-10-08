package com.codewithMeet.scm.scm0_2;

import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import com.codewithMeet.scm.scm0_2.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Scm02Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Scm02Application.class, args);
    }

    @Autowired
    UserRepo userRepo;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setEmail("admin@gmail.com");
//        admin.setEmailverified(true);
//        admin.setPhonenumber("8849765094");
//        admin.setPhoneverified(true);
//        admin.setEnabled(true);
//        admin.setAbout("testing account");
//        admin.setPassword(passwordEncoder.encode("admin"));
//
//        if (userRepo.findByEmail("admin@gmail.com").isPresent()) {
//            System.out.println("User already exists");
//        } else {
//            userRepo.save(admin);
//        }


    }
}

