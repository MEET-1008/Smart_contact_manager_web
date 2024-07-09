package com.codewithbrinda.scm.scm0_2.helper;

import com.codewithbrinda.scm.scm0_2.Service.UserService;
import com.codewithbrinda.scm.scm0_2.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {




        // agar email is password se login kiya hai to : email kaise nikalenge
        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = "";

            if (clientId.equalsIgnoreCase("google")) {

                // sign with google
                System.out.println("Getting email from google");
                email = oauth2User.getAttribute("email");

            } else if (clientId.equalsIgnoreCase("github")) {

                // sign with github
                System.out.println("Getting email from github");
                email = oauth2User.getAttribute("email");
            }

            // sign with facebook
            return email;

        } else {

            System.out.println("Getting data from local database");
            User user = (User) authentication.getPrincipal();
            return  user.getEmail();
        }


    }
}