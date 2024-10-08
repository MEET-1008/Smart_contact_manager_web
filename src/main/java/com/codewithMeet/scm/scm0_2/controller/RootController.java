package com.codewithMeet.scm.scm0_2.controller;
//package com.codewithbrinda.scm.scm0_2.controller;
//
//import Service.com.codewithMeet.scm.scm0_2.UserService;
//import entities.com.codewithMeet.scm.scm0_2.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class rootController {
//
//
//    @Autowired
//    UserService userService;
//
//
//    @ModelAttribute
//    public void addlogininformation (Model model , Authentication  authentication){
//        if (authentication == null) {
//            return;
//        }
//
//        var Tokan = (OAuth2AuthenticationToken) authentication;
//        String authorizedClientRegistrationId = Tokan.getAuthorizedClientRegistrationId();
//
//        var user = (DefaultOAuth2User) authentication.getPrincipal();
//
//
//
////        jo login google thi karo to
//        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
//
//            User user1 = userService.getUserByEmail(user.getAttribute("email"));
//
//            System.out.println(" user email id is :-  " + user1.getEmail());
//            System.out.println(" user user id is :-  " + user1.getUsername());
//
//            model.addAttribute("user", user1);
//        }
//
//
//
////        jo login github thi karo tot
//       else  if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
//            User user1 = userService.getUserByEmail(user.getAttribute("email"));
//            model.addAttribute("user", user1);
//        }
//
//
//
//
//        else {
//            User user1 = userService.getUserByEmail(authentication.getName());
//            System.out.println( " meet user "+user1);
//            model.addAttribute("user", user1);
//
//        }
//
//
//    }
//
//}
//
//


import com.codewithMeet.scm.scm0_2.Service.ContactService;
import com.codewithMeet.scm.scm0_2.Service.PaymentService;
import com.codewithMeet.scm.scm0_2.Service.UserService;
import com.codewithMeet.scm.scm0_2.entities.Payment;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.codewithMeet.scm.scm0_2.helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class RootController {


    @Autowired
    private UserService userService;


    @Autowired
    private ContactService contactService;

    @Autowired
    private PaymentService paymentService;


    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");

        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        System.out.println("User logged in: " + "{ " + email + " }");
        // database se data ko fetch : get user from db :
//        User user = userService.getUserByEmail("jay@gmail.com");

        System.out.println(" user finding..." + user);
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        int totalAllowedContacts = contactService.getTotalAllowedContacts(user.getUserid());
        int contactsCount = contactService.getContactCount(user.getUserid());
        if (totalAllowedContacts <  contactsCount) {
            model.addAttribute("notis", true);
        }
        else
            model.addAttribute("notis", false);


        String profilepic = user.getProfilepic();
model.addAttribute("profilepic", profilepic);
        Payment order = paymentService.findPaymentById(user.getPaymentId());
        model.addAttribute("order", order);
        model.addAttribute("expiryDate", user.getExpiryDate());
        model.addAttribute("totalAllowedContacts", totalAllowedContacts);
        model.addAttribute("subscriptionplan", user.getSubscriptionPlan());
        model.addAttribute("user", user);
        model.addAttribute("userid", user.getUserid());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("contactsCount", contactsCount);


    }
}
