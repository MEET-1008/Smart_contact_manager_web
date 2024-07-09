package com.codewithMeet.scm.scm0_2.controller;

import com.codewithMeet.scm.scm0_2.entities.Contact;
import com.codewithMeet.scm.scm0_2.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ContactApi {

    @Autowired
    ContactService contactService;

    @GetMapping("/contact/{id}")
    public Contact getById(@PathVariable int id ){
        return contactService.getContactById(id) ;
    }
}
