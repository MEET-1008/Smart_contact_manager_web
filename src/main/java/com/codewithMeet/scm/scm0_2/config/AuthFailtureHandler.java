package com.codewithMeet.scm.scm0_2.config;

import java.io.IOException;

import com.codewithMeet.scm.scm0_2.helper.MessageHelper;
import com.codewithMeet.scm.scm0_2.helper.MessageType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailtureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof DisabledException) {

            // user is disabled
            HttpSession session = request.getSession();
            session.setAttribute("message", MessageHelper.builder().content( " user is disable...! " ).type(MessageType.red).build());

            response.sendRedirect("/login");
        } else {
            response.sendRedirect("/login?error=true");
            // request.getRequestDispatcher("/login").forward(request, response);

        }

    }

}