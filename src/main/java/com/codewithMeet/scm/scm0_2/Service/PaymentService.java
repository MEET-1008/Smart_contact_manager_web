package com.codewithMeet.scm.scm0_2.Service;

import com.codewithMeet.scm.scm0_2.Repo.PaymentRepo;
import com.codewithMeet.scm.scm0_2.Repo.UserRepo;
import com.codewithMeet.scm.scm0_2.Service.impl.SecurityCustomUserDetailService;
import com.codewithMeet.scm.scm0_2.entities.Payment;
import com.codewithMeet.scm.scm0_2.entities.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.secret.key}")
    private String keySecret;


    private RazorpayClient client;

    public Payment findPaymentById(String id) {
        return paymentRepo.findByRazorPayOrderId(id);
    }

    public void deletAllByUserId(int id) {
        userRepo.findById(id).ifPresent(user -> {
            user.setPaymentId(null);
            user.setSubscription(false);
            user.setSubscriptionPlan("FREE");
            user.setExpiryDate("NULL");
            userRepo.save(user);
        });

    }


    public List<Payment> showAllPayment(int id ){
        return paymentRepo.findByUserUserid(id);
    }



    public Payment createOrder(Payment payment , String email) throws Exception {

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", payment.getAmount() * 100); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", String.valueOf(payment.getEmail()));

        this.client = new RazorpayClient(keyId, keySecret);
        Order razorPayOrder = client.orders.create(orderRequest);

        payment.setRazorPayOrderId(razorPayOrder.get("id"));
        payment.setOrderStatus(razorPayOrder.get("status"));
        Optional<User> email1 = userRepo.findByEmail(email);
        payment.setUser(email1.orElse(new User()));
        LocalDateTime ldt = LocalDateTime.now();

        payment.setOrderDate(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(ldt));
        paymentRepo.save(payment);

        return payment;
    }

    public Payment verifyPaymentAndUpdateOrderStatus(Map<String, String> respPayload) {
        Payment payment = null;
        try {

            String razorpayOrderId = respPayload.get("razorpay_order_id");
            String razorpayPaymentId = respPayload.get("razorpay_payment_id");
            String razorpaySignature = respPayload.get("razorpay_signature");

            // Verify the signature to ensure the payload is genuine
            boolean isValidSignature = verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

            if (isValidSignature) {
                payment = paymentRepo.findByRazorPayOrderId(razorpayOrderId);
                if (payment != null) {
                    payment.setOrderStatus("CONFIRMED");
                    User user = payment.getUser();
                    user.setSubscription(true);
                    user.setPaymentId(payment.getRazorPayOrderId());
                    LocalDateTime ldt = LocalDateTime.now();
                    LocalDateTime expDate = ldt.plusMonths(1);
                    user.setExpiryDate(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(expDate));
                    payment.setOrderDate(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(ldt));
                    if(payment.getAmount() == 100){
                        user.setSubscriptionPlan("BASIC");
                    }else if (payment.getAmount() == 200){
                        user.setSubscriptionPlan("PRO");
                    }else if (payment.getAmount() == 300){
                        user.setSubscriptionPlan("PREMIUM");
                    }
                    userRepo.save(user);
                    paymentRepo.save(payment);
                }
            } else {
                System.out.println("invalid");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return payment;
    }

    private boolean verifySignature(String orderId, String paymentId, String signature) throws RazorpayException {
        String generatedSignature = HmacSHA256(orderId + "|" + paymentId, keySecret);
        return generatedSignature.equals(signature);
    }

    private String HmacSHA256(String data, String key) throws RazorpayException {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(key.getBytes(),
                    "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes());
            return new String(org.apache.commons.codec.binary.Hex.encodeHex(hash));
        } catch (Exception e) {
            throw new RazorpayException("Failed to calculate signature.", e);
        }
    }
}
