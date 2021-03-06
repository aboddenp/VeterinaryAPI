package com.bodden.veterinaryapi.controllers;

import com.bodden.veterinaryapi.models.Appointment;
import com.bodden.veterinaryapi.services.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public Collection<Appointment> getAllAppointments() {

        return appointmentService.getAppointments();
    }

    @GetMapping("/appointments/{appId}")
    public Appointment getAppointment(@PathVariable long appId) {
        return appointmentService.getAppointment(appId);
    }

    @GetMapping("/pets/{petId}/appointments")
    public Collection<Appointment> getAllAppointmentsByPetId(@PathVariable(value = "petId") Long petId) {
        return appointmentService.appointmentByPet(petId);
    }

    @PostMapping("/pets/{petId}/appointments")
    public Appointment createAppointment(@PathVariable(value = "petId") Long petId,
                                         @RequestBody Appointment appointment) {
        return appointmentService.createAppointment(petId, appointment);
    }

    @PutMapping("/pets/{petId}/appointments/{appointmentId}")
    public Appointment updateAppointment(@PathVariable(value = "petId") Long petId,
                                         @PathVariable(value = "appointmentId") Long appointmentId,
                                         @RequestBody Appointment appointmentRequest) {
        return appointmentService.updateAppointment(appointmentId, petId, appointmentRequest);
    }

    @DeleteMapping("/pets/{petId}/appointments/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable(value = "petId") Long petId,
                                               @PathVariable(value = "appointmentId") Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId, petId);
        return ResponseEntity.ok().build();
    }
}