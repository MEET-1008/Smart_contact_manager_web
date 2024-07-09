package com.codewithbrinda.scm.scm0_2.config;

import com.codewithbrinda.scm.scm0_2.Service.impl.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenicationSuccessHandler handler;


 /*


//  InMemoryUserDetailsManager

    @Bean
    public  InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("meet")
                .password("meet")
                .roles("USER")
                .build();

        UserDetails user2 = User
                .withUsername("admin")
                .password("admin")
                .roles("ADMIN" ,  "USER")
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }



  */


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailService);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        url configure  url is private or not
        http.authorizeHttpRequests(authorize -> {
//          authorize.requestMatchers("/home" , "/signup" ,"/login").permitAll();
            authorize.requestMatchers("user/**").authenticated();
            authorize.requestMatchers("admin/**").authenticated();
            authorize.anyRequest().permitAll();
        });


//        http.formLogin(Customizer.withDefaults());
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
//          formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });


        http.csrf(AbstractHttpConfigurer::disable);
        // oauth configurations

        http.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

