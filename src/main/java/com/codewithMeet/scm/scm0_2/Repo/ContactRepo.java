package com.codewithMeet.scm.scm0_2.Repo;

import com.codewithMeet.scm.scm0_2.entities.Contact;
import com.codewithMeet.scm.scm0_2.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.userid = :UserId")
    List<Contact> findByUserId(@Param("UserId") int userId);


    Page<Contact> findByUserAndNameContainingOrPhoneNumberContainingOrEmailContaining (User user , String key , String key1 , String key3 ,  Pageable pageable);
 }
