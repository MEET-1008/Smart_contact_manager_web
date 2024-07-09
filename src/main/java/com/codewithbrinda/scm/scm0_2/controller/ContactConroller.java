package com.codewithbrinda.scm.scm0_2.controller;

import com.codewithbrinda.scm.scm0_2.Repo.ContactRepo;
import com.codewithbrinda.scm.scm0_2.Service.ContactService;
import com.codewithbrinda.scm.scm0_2.Service.ImgService;
import com.codewithbrinda.scm.scm0_2.Service.UserService;
import com.codewithbrinda.scm.scm0_2.entities.Contact;
import com.codewithbrinda.scm.scm0_2.entities.User;
import com.codewithbrinda.scm.scm0_2.forms.ContactForm;
import com.codewithbrinda.scm.scm0_2.forms.ContactSearchForm;
import com.codewithbrinda.scm.scm0_2.helper.AppConstants;
import com.codewithbrinda.scm.scm0_2.helper.Helper;
import com.codewithbrinda.scm.scm0_2.helper.MessageHelper;
import com.codewithbrinda.scm.scm0_2.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactConroller {


    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    ImgService imgService;
    @Autowired
    private ContactRepo contactRepo;

    @GetMapping("/add")
    // add contact page: handler
    public String addContactView(Model model) {
        System.out.println("********* Get request in contact form ********");
        ContactForm contactForm = new ContactForm();

//        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);

        System.out.println(contactForm.toString());
        return "user/add_contact";
    }

    @PostMapping("add")
    public String savecontact(@Valid @Validated ContactForm contactForm, BindingResult result, Authentication authentication, Model model, HttpSession session) throws IOException {
        System.out.println("********* post request in contact form ********");
        if (result.hasErrors()) {
//
//            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
//
            session.setAttribute("message", MessageHelper.builder().content("Please correct the following errors").type(MessageType.red).build());


            return "user/add_contact";
        }


        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        Contact contact = getContact(contactForm, user);

        contactService.save(contact);

        session.setAttribute("message", MessageHelper.builder().content("your contact added successfully").type(MessageType.green).build());


        return "user/add_contact";

    }

    private Contact getContact(ContactForm contactForm, User user) throws IOException {

        Contact contact = new Contact();



        if(contactForm.getContactImage() != null  && !contactForm.getContactImage().isEmpty()){
            System.out.println("img is null or not this chake .....  " + contactForm.getContactImage().getOriginalFilename());
            System.out.println(" file name ::------ " + contactForm.getContactImage().getOriginalFilename());

            String FilURL = this.imgService.UploadIMG(contactForm.getContactImage());
            contact.setContactImage(FilURL);
        }
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.getFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        return contact;
    }


    // view contacts

    @GetMapping("/all")
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        // load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getContactsByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    // search handler

//    @RequestMapping("/search")
//    public String searchHandler(
//
//            @ModelAttribute ContactSearchForm contactSearchForm,
//            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
//            @RequestParam(value = "direction", defaultValue = "asc") String direction,
//            Model model,
//            Authentication authentication) {
//
//        System.out.println("field { " +contactSearchForm.getField() + " } keyword { "+ contactSearchForm.getValue() +"} " );
//
//        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
//
//        Page<Contact> pageContact = null;
//        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
//            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
//        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
//            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
//        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
//            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,direction, user);
//        }
//
//        System.out.println("pageContact { "+ pageContact + "}");
//
//        model.addAttribute("contactSearchForm", contactSearchForm);
//
//        model.addAttribute("pageContact", pageContact);
//
//        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
//
//        return "user/search";
//    }

 @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        System.out.println("field { " +contactSearchForm.getField() + " } keyword { "+ contactSearchForm.getValue() +"} " );

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = contactService.searchKeyword(contactSearchForm.getValue(),size,page,sortBy,direction,user);


        System.out.println("pageContact { "+ pageContact + "}");

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }




    @RequestMapping("/delete/{id}")
    public String deletcontact(@PathVariable int id , HttpSession session){

        contactService.deleteContact(id);
        System.out.println("contct deleted " +id );
        session.setAttribute("message", MessageHelper.builder().content("your contact deleted successfully").type(MessageType.green).build());


        return "redirect:/user/contacts/all";
    }


    @RequestMapping("/view_update/{id}")
    public String updateContact(@PathVariable int id , HttpSession session , Model model){

        Contact contact = contactService.getContactById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setFavorite(contact.getFavorite());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setPicture(contact.getContactImage());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactid" , id);
        return "user/update_contact";

    }




    @PostMapping("/update/{id}")
        public String updateContactPost(@PathVariable int id ,@Valid @ModelAttribute ContactForm contactform , BindingResult result ,   HttpSession session , Model model) throws IOException {


        if (result.hasErrors()){
            model.addAttribute("contactid" , id);
            System.out.println(contactform);
            System.out.println("get photo url :-" + contactform.getPicture());
            return "user/update_contact";
        }

            Contact contact = contactService.getById(id);
            contact.setId(id);
            contact.setName(contactform.getName());
            contact.setFavorite(contactform.getFavorite());
            contact.setEmail(contactform.getEmail());
            contact.setPhoneNumber(contactform.getPhoneNumber());
            contact.setAddress(contactform.getAddress());
            contact.setDescription(contactform.getDescription());
            contact.setLinkedInLink(contactform.getLinkedInLink());
            contact.setWebsiteLink(contactform.getWebsiteLink());


            if(contactform.getContactImage() != null && !contactform.getContactImage().isEmpty()){
                String fileUrl = imgService.UploadIMG(contactform.getContactImage());
                contact.setContactImage(fileUrl);
                contactform.setPicture(fileUrl);
            }

        model.addAttribute("message", MessageHelper.builder().content("your contact update  successfully").type(MessageType.green).build());
        Contact contact1 = contactService.updateContact(contact);
//        return "user/view_update/" + id  ;
        return "redirect:/user/contacts/view_update/" + id;
        }
}
