package com.bodden.VeterinaryAPI.Controllers;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Repositories.AppointmentRepository;
import com.bodden.VeterinaryAPI.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/pets/{petId}/appointments")
    public List<Appointment> getAllAppointmentsByPetId(@PathVariable (value = "petId") Long petId) {
        return appointmentRepository.findByPetId(petId);
    }

    @PostMapping("/pets/{petId}/appointments")
    public Appointment createAppointment(@PathVariable (value = "petId") Long petId,
                                 @RequestBody Appointment appointment) {
        return petRepository.findById(petId).map(pet -> {
            appointment.setPet(pet);
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new ResourceNotFoundException("PetId " + petId + " not found"));
    }

    @PutMapping("/pets/{petId}/appointments/{appointmentId}")
    public Appointment updateAppointment(@PathVariable (value = "petId") Long petId,
                                 @PathVariable (value = "appointmentId") Long appointmentId,
                                         @RequestBody Appointment appointmentRequest) {
        if(!petRepository.existsById(petId)) {
            throw new ResourceNotFoundException("PetId " + petId + " not found");
        }

        return appointmentRepository.findById(appointmentId).map(appointment -> {
            appointment.setLocalDate(appointmentRequest.getLocalDate());
            appointment.setLocalTime(appointmentRequest.getLocalTime());
            appointment.setService(appointmentRequest.getService());
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new ResourceNotFoundException("AppointmentId " + appointmentId + "not found"));
    }

    @DeleteMapping("/pets/{petId}/appointments/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable (value = "petId") Long petId,
                                           @PathVariable (value = "appointmentId") Long appointmentId) {
        return appointmentRepository.findByIdAndPetId(appointmentId, petId).map(appointment -> {
            appointmentRepository.delete(appointment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId + " and petId " + petId));
    }
}