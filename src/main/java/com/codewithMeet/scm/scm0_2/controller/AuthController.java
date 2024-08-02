package com.codewithMeet.scm.scm0_2.controller;

import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.codewithMeet.scm.scm0_2.helper.MessageHelper;
import com.codewithMeet.scm.scm0_2.helper.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // verify email

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {
            // user fetch hua hai :: process karna hai

            if (user.getEmailToken().equals(token)) {
                user.setEmailverified(true);
                user.setEnabled(true);
                userRepo.save(user);

                session.setAttribute("message", MessageHelper.builder()   .content("You email is verified. Now you can login  ").type(MessageType.green).build());

                return "success_page";
            }


            session.setAttribute("message", MessageHelper.builder().content("Email not verified ! Token is not associated with user .").type(MessageType.red).build());

            return "error_page";

        }

        session.setAttribute("message", MessageHelper.builder().content("Email not verified ! Token is not associated with user .").type(MessageType.red).build());

        return "error_page";
    }

}