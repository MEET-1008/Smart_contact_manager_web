package com.codewithMeet.scm.scm0_2.Service;


import jakarta.mail.MessagingException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface EmailService {

    // send email to single persion
    void SendEmail(String to, String subject, String body) ;

    // send email to multiple person
    void SendEmail(String[] to, String subject, String body);

    // sendemailwithHTML
    void SendEmailWithHtml(String to, String subject, String htmlContent) throws MessagingException;

    //sendmailWithFile
    void sendemailWithFile(String to , String Subject, String body, File file) throws MessagingException;

    // sendemailWithFileeithio
    void sendemailWithFile(String to , String Subject, String body, InputStream is) throws MessagingException, IOException;


}
