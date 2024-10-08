package com.codewithMeet.scm.scm0_2.Service;

import com.codewithMeet.scm.scm0_2.entities.User;
import jakarta.mail.MessagingException;

import java.util.Optional;

public interface UserService  {

    void saveUsers(User user) throws MessagingException;
    Optional<User> getUserById(int id);
    User getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);

    User upateUser(User user);

    void deleteUser(int id);

    boolean isUserExistsBYEmail(String emailId);

    boolean isUserExists(int id);

    Optional<User> getAllUser ();

    boolean changePassword(String email ,String oldPassword, String newPassword);

    boolean changePasswordOth2(String email ,String OTP, String newPassword);





}
