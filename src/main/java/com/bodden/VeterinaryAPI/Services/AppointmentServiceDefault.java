package com.bodden.VeterinaryAPI.Services;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Repositories.AppointmentRepository;
import com.bodden.VeterinaryAPI.Repositories.PetRepository;
import com.sun.istack.NotNull;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class AppointmentServiceDefault implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PetRepository petRepository;

    @Override
    public Appointment createAppointment(Long petId, Appointment appointment) {
        return petRepository.findById(petId).map(pet -> {
            appointment.setPet(pet);
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> notFound(petId));
    }

    @Override
    public Appointment updateAppointment(Long id, Long petId, Appointment appointmentRequest) {
        return appointmentRepository.findByIdAndPetId(id,petId).map(appointment -> {
            appointment.setLocalDate(appointmentRequest.getLocalDate());
            appointment.setLocalTime(appointmentRequest.getLocalTime());
            appointment.setService(appointmentRequest.getService());
            appointment.setPet(appointment.getPet());
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> notFound(id,petId));
    }

    @Override
    public boolean deleteAppointment(Long id, Long petId) {
        return appointmentRepository.findByIdAndPetId(id, petId).map(appointment -> {
            appointmentRepository.delete(appointment);
            return true;
        }).orElseThrow(() -> notFound(id,petId));
    }

    @Override
    public Collection<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()-> notFound(id));
    }

    @Override
    public Collection<Appointment> appointmentByPet(Long id) {
        return appointmentRepository.findByPetId(id).orElseThrow(()-> notFound(id));
    }

    private ResourceNotFoundException notFound(Long petId){
        return new ResourceNotFoundException("PetId " + petId + " not found");
    }

    private ResourceNotFoundException notFound(Long id, Long petId){
        return new ResourceNotFoundException("Appointment not found with id " + id + " and petId " + petId);
    }

}
