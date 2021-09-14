package com.bodden.veterinaryapi.repositories;

import com.bodden.veterinaryapi.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Optional<Payment> findByAppointmentId(Long paymentId);
}
