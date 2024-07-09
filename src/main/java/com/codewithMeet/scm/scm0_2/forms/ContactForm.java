package com.codewithMeet.scm.scm0_2.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "User name is Required..")
    @Size(min = 3 , message = "min 3 characters required..")
    private String name;


    @NotBlank(message = "User phoneNumber is Required..")
    @Size(min = 10 , message = "min 10 characters required..")
    @Size(max = 12 , message = "max 12 number enter")
    private String phoneNumber;

    @Email
    private String email;


    private String address;

    private String description;

    private Boolean favorite=false;

    private String websiteLink;

    private String linkedInLink;

    private MultipartFile contactImage;

    private String Picture;

}
