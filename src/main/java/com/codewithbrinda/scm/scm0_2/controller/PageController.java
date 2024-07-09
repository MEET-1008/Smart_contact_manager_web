package com.codewithbrinda.scm.scm0_2.controller;

import com.codewithbrinda.scm.scm0_2.Repo.UserRepo;
import com.codewithbrinda.scm.scm0_2.Service.UserService;
import com.codewithbrinda.scm.scm0_2.entities.User;
import com.codewithbrinda.scm.scm0_2.forms.UserForms;
import com.codewithbrinda.scm.scm0_2.helper.MessageHelper;
import com.codewithbrinda.scm.scm0_2.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;


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
    public String login() {
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

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String registerProcess(@Valid @ModelAttribute UserForms userForms, HttpSession session, BindingResult result) {

        //fetch the data for outperform
//        System.out.println("userForms is " + userForms);

        // validate data
//        if (result.hasErrors()){
//            return "signup";
//        }

        System.out.println(userForms);

//        fetch the data for userForm and save data in database
        if (result.hasErrors()) {
            session.setAttribute("message", MessageHelper.builder().content("user already signup...! ").type(MessageType.red).build());

            return "redirect:/signup";
        }

        User user2 = userRepo.findByEmail(userForms.getEmail()).orElse(null);

        if (user2 == null) {
            userService.saveUsers(getUser(userForms));
            System.out.println("user saved email id is :" + userForms.getEmail());
            session.setAttribute("message", MessageHelper.builder().content("your contact added successfully").type(MessageType.green).build());


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
