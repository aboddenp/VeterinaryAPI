package com.bodden.VeterinaryAPI.Controllers;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Payment;
import com.bodden.VeterinaryAPI.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("payments")
    public Collection<Payment> getPayments(){
        return paymentRepository.findAll();
    }

    @GetMapping("/appointment/{appId}/payment")
    public Payment getPayment(@PathVariable Long appId){
        return paymentRepository.findByAppointmentId(appId)
                .orElseThrow(()->new ResourceNotFoundException("Appointment with id: "+appId+" not found"));
    }

    @PutMapping("/appointment/{appId}/payment/pay")
    public Payment ProcessPayment(@PathVariable Long appId){
        return null;
    }





}
