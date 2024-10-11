package com.codewithMeet.scm.scm0_2.controller;

import com.codewithMeet.scm.scm0_2.Service.ContactService;
import com.codewithMeet.scm.scm0_2.Service.PaymentService;
import com.codewithMeet.scm.scm0_2.Service.UserService;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.codewithMeet.scm.scm0_2.helper.Helper;
import com.codewithMeet.scm.scm0_2.helper.MessageHelper;
import com.codewithMeet.scm.scm0_2.helper.MessageType;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;
    @Autowired
    private com.codewithMeet.scm.scm0_2.Service.otpService otpService;

    //    user Dashboard page
    @PostMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication) {

        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        System.out.println(" user desboared her ");
//        contactService.getTotalAllowedContacts()
        return "user/dashboard";
    }

    @GetMapping("/payment/cancel/{id}")
    public String cancel(@PathVariable int id, Model model) {
        paymentService.deletAllByUserId(id);
        System.out.println(" payment deleted. with user id :- " + id);
        return "user/dashboard";
    }


    //    user profile page
    @GetMapping("/dashboard")
    public String userProfile(Model model, Authentication authentication) {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userDashboardget(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        return "user/profile";
    }

    @PostMapping("/profile")
    public String userDashboardpost(Model model, Authentication authentication) {
        return "user/profile";
    }


    @GetMapping("/changepassword")
    public String changepassword(Model model, Authentication authentication) {
        return "user/Passwordchange";
    }


    @PostMapping("/changePassword")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, Authentication authentication,
            HttpSession session) {
        if (authentication == null) {
            return "redirect:/login";
        }


        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("message", MessageHelper.builder().content("New passwords do not match.").type(MessageType.red).build());
            return "redirect:/user/changepassword";
        }


        String email = Helper.getEmailOfLoggedInUser(authentication);

        boolean result = userService.changePassword(email, currentPassword, newPassword);

        if (result) {
            session.setAttribute("message", MessageHelper.builder().content("Password changed successfully.").type(MessageType.green).build());
        } else {
            session.setAttribute("message", MessageHelper.builder().content("Current password is incorrect.").type(MessageType.yellow).build());
        }
        return "redirect:/user/changepassword";
    }


    @GetMapping("/changePasswordOuth2")
    public String changePasswordOuth2(Model model, Authentication authentication, HttpSession session) throws MessagingException {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        otpService.generateOTP(email);
        session.setAttribute("message", MessageHelper.builder().content("otp send successfully.").type(MessageType.green).build());
        return "user/change-Password-outh2";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model, Authentication authentication, HttpSession session) throws MessagingException {
        return "user/change-Password-outh2";
    }


    @PostMapping("/changePasswordOuth2")
    public String sendotpemail(
            @RequestParam("OTP") String OTP,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model, Authentication authentication, HttpSession session) throws MessagingException {

        if (authentication == null) {
            return "redirect:/login";
        }


        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("message", MessageHelper.builder().content("New passwords do not match.").type(MessageType.red).build());
            return "redirect:/user/changePasswordOuth2";
        }


        System.out.println(OTP + "22222222222222******");
        String email = Helper.getEmailOfLoggedInUser(authentication);

        System.out.println(OTP + "/****");
        boolean result = userService.changePasswordOth2(email, OTP, newPassword);

        if (result) {
            session.setAttribute("message", MessageHelper.builder().content("Password changed successfully.").type(MessageType.green).build());
        } else {
            session.setAttribute("message", MessageHelper.builder().content("OTP is incorrect.").type(MessageType.yellow).build());
        }

        return "redirect:/user/changepassword";
    }


}