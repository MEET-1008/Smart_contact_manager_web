package com.codewithMeet.scm.scm0_2.controller;

import com.codewithMeet.scm.scm0_2.Service.UserService;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.codewithMeet.scm.scm0_2.forms.ContactForm;
import com.codewithMeet.scm.scm0_2.forms.UserForms;
import com.codewithMeet.scm.scm0_2.helper.MessageHelper;
import com.codewithMeet.scm.scm0_2.helper.MessageType;
import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/delet/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        System.out.println("user id is de le= "+id);
        return "home";
    }


    @RequestMapping("/home")

    public String home(Model model) {

        System.out.println("Home is on");
        return "home";
    }

    @RequestMapping("/")

    public String index() {
        return "redirect:/home";
    }

//    about router      

    @RequestMapping("/about")
    public String about() {
        System.out.println("about page");
        return "about";
    }

//    services router

    @RequestMapping("/services")
    public String services() {
        System.out.println("services page");
        return "services";
    }

//   contact page

    @RequestMapping("/contact")
    public String contact(Model model) {
        System.out.println("contact page");
        return "contact";
    }

    @GetMapping("/login")


    public String login(Authentication authentication) {
        if (authentication != null) {
            return "redirect:user/profile";
        }

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        UserForms userForm = new UserForms();

//        set the default value in the form
//        userForm.setUsername("Meet");
//        userForm.setPhonenumber("8849765094");
//        userForm.setEmail("Meet");
//        userForm.setAbout("meet profile");

        model.addAttribute("userForm", userForm);

        return "signup";
    }

    @PostMapping( "/do-register")
    public String registerProcess(@Valid @Validated UserForms userForms, BindingResult result , HttpSession session) throws MessagingException {

        //fetch the data for outperform
        System.out.println( "print the contact form :- "+userForms);

        if (result.hasErrors()) {
            session.setAttribute("message", MessageHelper.builder().content("Please correct the following errors").type(MessageType.red).build());
            return "redirect:/signup";
        }

//        fetch the data for userForm and save data in database
        User user2 = userRepo.findByEmail(userForms.getEmail()).orElse(null);

        if (user2 == null) {
            userService.saveUsers(getUser(userForms));

            System.out.println("user saved email id is :" + userForms.getEmail());
            session.setAttribute("message", MessageHelper.builder().content("user save successfully").type(MessageType.green).build());


        }
        else
        {
            session.setAttribute("message", MessageHelper.builder().content("user already signup...! ").type(MessageType.red).build());
        }


        return "redirect:/signup";
    }


    private static User getUser(UserForms userForms) {
        User user1 = new User();
        user1.setUsername(userForms.getUsername());
        user1.setEmail(userForms.getEmail());
        user1.setPassword(userForms.getPassword());
        user1.setAbout(userForms.getAbout());
        user1.setPhonenumber(userForms.getPhoneNumber());

        user1.setProfilepic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.m.wikipedia.org%2Fwiki%2FFile%3ADefault_pfp.svg&psig=AOvVaw28VcDklYcHi_KPECHdyFhq&ust=1717472793921000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNCPtfHCvoYDFQAAAAAdAAAAABAE");
        return user1;
    }



}
