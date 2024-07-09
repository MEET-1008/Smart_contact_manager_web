package com.codewithMeet.scm.scm0_2.Service.impl;

import com.codewithMeet.scm.scm0_2.entities.Contact;
import com.codewithMeet.scm.scm0_2.exception.ResouecenotfoundException;
import com.codewithMeet.scm.scm0_2.Repo.ContactRepo;
import com.codewithMeet.scm.scm0_2.Service.ContactService;
import com.codewithMeet.scm.scm0_2.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepo contactRepo;


    @Override
    public Contact save(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    public Contact getContactById(int id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResouecenotfoundException("contact id", "id", id));
    }

    @Override
    public Contact getById(int id) {
        return contactRepo.findById(id).orElse(null);
    }


    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }


    @Override
    public void deleteContact(int id) {
        contactRepo.deleteById(id);

    }

    @Override
    public Contact updateContact(Contact contactform) {

        Contact contact = contactRepo.findById(contactform.getId()).orElseThrow(() -> new ResouecenotfoundException("", "", contactform.getId()));
        contact.setName(contactform.getName());
        contact.setFavorite(contactform.getFavorite());
        contact.setEmail(contactform.getEmail());
        contact.setPhoneNumber(contactform.getPhoneNumber());
        contact.setAddress(contactform.getAddress());
        contact.setDescription(contactform.getDescription());
        contact.setLinkedInLink(contactform.getLinkedInLink());
        contact.setWebsiteLink(contactform.getWebsiteLink());
        contact.setContactImage(contactform.getContactImage())  ;

        return   contactRepo.save(contact);

    }

    @Override
    public List<Contact> getContactsByUserId(int UserId) {
        return List.of();
    }

    @Override
    public Page<Contact> getContactsByUser(User user, int page, int size, String SortBy, String Direction) {
        Sort sort = Direction.equals("desc") ? Sort.by(SortBy).descending() : Sort.by(SortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user , pageable);
    }


    @Override
    public Page<Contact> searchKeyword(String key, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContainingOrPhoneNumberContainingOrEmailContaining(user, key,key , key ,pageable);
    }


}

