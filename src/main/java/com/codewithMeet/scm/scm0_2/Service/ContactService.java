package com.codewithMeet.scm.scm0_2.Service;

import com.codewithMeet.scm.scm0_2.entities.Contact;
import com.codewithMeet.scm.scm0_2.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {


    Contact save(Contact contact);

    Contact getContactById(int id);

    Contact getById(int id);

    List<Contact> getAllContacts();

    void deleteContact(int id);

    Contact updateContact(Contact contact );

    List<Contact> getContactsByUserId(int UserId);

    Page<Contact> getContactsByUser(User user , int Page , int PageSize , String SortBy , String Direction );




    Page<Contact> searchKeyword (
            String key,
            int size,
            int page,
            String sortBy,
            String order,
            User user);


}
