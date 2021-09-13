package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    public Optional<Payment> findByPaymentId(Long paymentId);
}
