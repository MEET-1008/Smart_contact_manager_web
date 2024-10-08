package com.codewithMeet.scm.scm0_2.Service;

import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.codewithMeet.scm.scm0_2.exception.ResouecenotfoundException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.util.Optional;
import java.util.Random;

@Service
public class otpService {

    @Autowired
    EmailService emailService;
    @Autowired
    private UserRepo userRepo;


    public void generateOTP(String email) throws MessagingException {
        String otp = String.valueOf(new Random().nextInt(999999));
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResouecenotfoundException("user","email",email));
        user.setPasswordChangeOtp(otp);
        userRepo.save(user);
        sendPasswordResetEmail(email, user.getUsername() , otp);
    }


    public void sendPasswordResetEmail(String recipientEmail, String name, String otp) throws MessagingException {
        // Define the email template with placeholders
        String template = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Password Reset</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }
            .email-container {
                width: 100%%;  /* Escaped %% to prevent format errors */
                max-width: 600px;
                margin: 20px auto;
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                overflow: hidden;
            }
            .email-header {
                background-color: #FF6347;
                padding: 20px;
                text-align: center;
                color: #ffffff;
            }
            .email-body {
                padding: 30px;
            }
            .email-body h2 {
                margin-top: 0;
            }
            .otp-code {
                font-size: 5em;  /* Large OTP font size */
                color: #FF6347;
                text-align: center;
                margin: 20px 0;
                letter-spacing: 5px;
            }
            .email-footer {
                padding: 20px;
                text-align: center;
                font-size: 12px;
                color: #777;
                border-top: 1px solid #eaeaea;
            }
        </style>
    </head>
    <body>
        <div class="email-container">
            <div class="email-header">
                <h1>Password Reset Request</h1>
            </div>
            <div class="email-body">
                <h2>Hello, %s!</h2>
                <p>You recently requested to reset your password. Use the following OTP code to complete your request:</p>
                <div class="otp-code">%s</div>
                <p>If you did not request this change, you can ignore this email.</p>
            </div>
            <div class="email-footer">
                <p>Best regards,<br/>SCM</p>
            </div>
        </div>
    </body>
    </html>
    """;

        // Replace placeholders with actual values using String.format()
        String body = String.format(template, name, otp);
        emailService.SendEmailWithHtml(recipientEmail, "Password Reset OTP", body);
    }


}
