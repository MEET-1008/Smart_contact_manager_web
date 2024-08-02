package com.codewithMeet.scm.scm0_2.Service.impl;

import com.codewithMeet.scm.scm0_2.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class emailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;



    @Override
    public void SendEmail(String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage );
        System.out.println("Email has been sent...");
    }

    @Override
    public void SendEmail(String[] to, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
        System.out.println("Email has been sent...");
    }

    @Override
    public void SendEmailWithHtml(String to, String subject, String htmlContent) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
        System.out.println("Email has been sent...");

    }

    @Override
    public void sendemailWithFile(String to, String Subject, String body, File file) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setTo(to);
        helper.setSubject(Subject);
        helper.setText(body, true);
        helper.addAttachment(file.getName(), file);
        mailSender.send(mimeMessage);
        System.out.println("Email has been sent...");


    }


    @Override
    public void sendemailWithFile(String to, String Subject, String body, InputStream is) throws MessagingException, IOException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setTo(to);
        helper.setSubject(Subject);
        helper.setText(body, true);
        File file = new File("");
        Files.copy(is,file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        helper.addAttachment(fileSystemResource.getFilename(), file);
        mailSender.send(mimeMessage);
        System.out.println("Email has been sent...");
    }
}
