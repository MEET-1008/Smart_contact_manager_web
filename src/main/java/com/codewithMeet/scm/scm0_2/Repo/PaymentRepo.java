package com.codewithMeet.scm.scm0_2.Repo;

import com.codewithMeet.scm.scm0_2.Service.PaymentService;
import com.codewithMeet.scm.scm0_2.entities.Contact;
import com.codewithMeet.scm.scm0_2.entities.Payment;
import com.codewithMeet.scm.scm0_2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {

    public Payment findByRazorPayOrderId(String razorPayOrderId);

    List<Payment> findByUserUserid(int id);

    public void deleteAllByUserUserid(int id);

    @Query("SELECT c FROM Payment c WHERE c.user.userid = :UserId")
    List<Payment> findByUserId(@Param("UserId") int userId);
}

