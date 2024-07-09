package com.codewithbrinda.scm.scm0_2.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForms {

    @NotBlank(message = "User name is Required..")
    @Size(min = 3 , message = "min 3 characters required..")
    private String username;

    @Size(min = 8 , max = 12, message = "Min 10 character is required")
    @NotBlank(message="phone number Required")
    private String phoneNumber;

    @Email(message = "Invalid Email")
    @NotBlank(message = "email is Required..")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min =3 )
    private String password;



    @NotBlank( message = " About  Required ")
    private String about;


}
