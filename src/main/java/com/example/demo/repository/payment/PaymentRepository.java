package com.example.demo.repository.payment;

import com.example.demo.entity.payment.Payment;
import com.example.demo.entity.payment.PaymentPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, PaymentPK> {

    @Override
    Optional<Payment> findById(PaymentPK paymentPK);
}
