package com.codewithMeet.scm.scm0_2.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserForms {

    @NotBlank(message = "user name is Required..")
    @Size(min = 3 , message = "min 3 characters required..")
    private String username;

    @NotBlank(message="phone number Required")
    @Size(min = 8 , max = 12, message = "Min 10 character is required")
    private String phoneNumber;

    @NotBlank(message = "email is Required..")
    @Email(message = "Invalid Email")
    private String email;

    @Size(min =3 )
    @NotBlank(message = "password is required")
    private String password;

    @NotBlank( message = " About  Required ")
    private String about;

    private String expiryDate;

    private String subscriptionPlan;


}
