package com.codewithbrinda.scm.scm0_2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    //    user Dashboard page
    @PostMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        System.out.println(" user desboared her ");
        return "user/dashboard";
    }

    //    user profile page
    @GetMapping("/dashboard")
    public String userProfile(Model model, Authentication authentication) {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userDashboardget(Model model, Authentication authentication) {
        if(authentication == null){
            return "redirect:/login";
        }
        return "user/profile";
    }

    @PostMapping("/profile")
    public String userDashboardpost(Model model, Authentication authentication) {
        return "user/profile";
    }


}
