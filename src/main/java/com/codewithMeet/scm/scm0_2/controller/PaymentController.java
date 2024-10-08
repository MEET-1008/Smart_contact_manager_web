package com.codewithMeet.scm.scm0_2.controller;

import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import com.codewithMeet.scm.scm0_2.Service.PaymentService;
import com.codewithMeet.scm.scm0_2.entities.Payment;
import com.codewithMeet.scm.scm0_2.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService service;


    @GetMapping("/")
    public String init() {
        return "home";
    }

    @PostMapping(value = "/create-order", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Payment> purchaseCourse(@RequestBody Payment payment) throws Exception {


        Payment orderResp = service.createOrder(payment ,  payment.getEmail());

        return new ResponseEntity<>(orderResp, HttpStatus.OK);
    }

    @PostMapping("/payment-callback")
    public String handlePaymentCallback(@RequestParam Map<String, String> respPayload, Model model) {
        System.out.println(respPayload);
        Payment updatedOrder = service.verifyPaymentAndUpdateOrderStatus(respPayload);
        model.addAttribute("order", updatedOrder);
        return "user/success";
    }


}