package com.codewithMeet.scm.scm0_2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    private String name;
    private String email;
    private String phone;
    private Integer amount;
    private String orderStatus;
    private String razorPayOrderId;
    private String orderDate;

    @ManyToOne
    @JsonBackReference
    private User user;
}