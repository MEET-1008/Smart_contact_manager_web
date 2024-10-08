package com.codewithMeet.scm.scm0_2.Service.impl;

import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import com.codewithMeet.scm.scm0_2.Service.UserService;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.codewithMeet.scm.scm0_2.exception.ResouecenotfoundException;
import com.codewithMeet.scm.scm0_2.helper.AppConstants;
import com.codewithMeet.scm.scm0_2.helper.Helper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    emailServiceImpl emailService;

    @Autowired
    private UserRepo userRepo;


    public void sendVerificationEmail(String recipientEmail, String name, String verificationToken) throws MessagingException {
        String verificationLink = Helper.getLinkForEmailVerificatiton(verificationToken);

        // Define the email template with placeholders
        String template = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Email Verification</title>
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
                    background-color: #4CAF50;
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
                .email-button {
                    display: inline-block;
                    margin-top: 20px;
                    padding: 10px 20px;
                    background-color: #4CAF50;
                    color: #ffffff;
                    text-decoration: none;
                    border-radius: 5px;
                    font-weight: bold;
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
                    <h1>Verify Your Email</h1>
                </div>
                <div class="email-body">
                    <h2>Hello, %s!</h2>
                    <p>Thank you for registering on our platform. To complete your registration, please verify your email address by clicking the button below:</p>
                    <a class="email-button" href="%s">Verify Your Email</a>
                    <p>If you did not register for an account, please ignore this email.</p>
                </div>
                <div class="email-footer">
                    <p>Best regards,<br/>SCM</p>
                </div>
            </div>
        </body>
        </html>
        """;

        // Replace placeholders with actual values using String.format()
        String body = String.format(template, name, verificationLink);
        emailService.SendEmailWithHtml(recipientEmail, "Email Verification", body);
    }


    @Override
    public void saveUsers(User user) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        String emailtokan = UUID.randomUUID().toString();
        sendVerificationEmail(user.getEmail(), user.getUsername(), emailtokan);
        user.setEmailToken(emailtokan);
        user.setSubscriptionPlan("FREE");
        user.setPasswordChangeOtp("NULL");
        user.setExpiryDate("NULL");
        userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepo.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
//       return userRepo.findByEmail(email).orElseThrow(() -> new ResouecenotfoundException("email id ","id",email));

        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User upateUser(User user) {

        User user2 = userRepo.findById(user.getUserid()).orElseThrow(() -> new ResouecenotfoundException("user", "userid", user.getUserid()));
        user2.setUsername(user.getUsername());
        user2.setPassword(user.getPassword());
        user2.setEmail(user.getEmail());
        user2.setAbout(user.getAbout());
        user2.setPhonenumber(user.getPhonenumber());
        user2.setProfilepic(user.getProfilepic());
        user2.setProviderUserId(user.getProviderUserId());
        user2.setProvider(user.getProvider());
        user2.setEmailverified(user.isEmailverified());
        user2.setPhoneverified(user.isPhoneverified());

        return userRepo.save(user2);
    }

    @Override
    public void deleteUser(int id) {
        userRepo.deleteById(id);

    }

    @Override
    public boolean isUserExistsBYEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null;
    }

    @Override
    public boolean isUserExists(int id) {
        User user2 = userRepo.findById(id).orElse(null);
        return user2 != null;
    }

    @Override
    public Optional<User> getAllUser() {
        return Optional.empty();
    }

    @Override
    public boolean changePassword( String username ,String currentPassword, String newPassword) {

        // Find the user in the database
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null || !passwordEncoder.matches(currentPassword, user.getPassword()))
        {
            return false;
        }

        // Set the new password and save the user
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean changePasswordOth2( String username ,String OTP, String newPassword) {

        // Find the user in the database
        User user = userRepo.findByEmail(username).orElseThrow(() -> new ResouecenotfoundException("user", "userid", username));
        String passwordChangeOtp = user.getPasswordChangeOtp();
        if (!Objects.equals(passwordChangeOtp, OTP))
        {
            return false;
        }

        // Set the new password and save the user
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return true;
    }
}
