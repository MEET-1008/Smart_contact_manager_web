package com.codewithbrinda.scm.scm0_2.Service.impl;

import com.codewithbrinda.scm.scm0_2.Repo.UserRepo;
import com.codewithbrinda.scm.scm0_2.Service.UserService;
import com.codewithbrinda.scm.scm0_2.entities.User;
import com.codewithbrinda.scm.scm0_2.exception.ResouecenotfoundException;
import com.codewithbrinda.scm.scm0_2.helper.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
   private UserRepo userRepo;

    @Override
    public void saveUsers(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepo.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
//       return userRepo.findByEmail(email).orElseThrow(() -> new ResouecenotfoundException("email id ","id",email));

    return  userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User upateUser(User user) {

        User user2 = userRepo.findById(user.getUserid()).orElseThrow(() -> new ResouecenotfoundException("user","userid", user.getUserid()));
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
        User user2 = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepo.delete(user2);

    }

    @Override
    public boolean isUserExistsBYEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null;
    }

    @Override
    public boolean isUserExists(int id) {
      User user2 =  userRepo.findById(id).orElse(null);
        return user2 != null;
    }

    @Override
    public Optional<User> getAllUser() {
        return Optional.empty();
    }
}
