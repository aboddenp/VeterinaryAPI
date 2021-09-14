package com.bodden.veterinaryapi.services.implementations;

import com.bodden.veterinaryapi.exceptions.InvalidDataException;
import com.bodden.veterinaryapi.exceptions.ResourceNotFoundException;
import com.bodden.veterinaryapi.models.Appointment;
import com.bodden.veterinaryapi.models.AppointmentHistory;
import com.bodden.veterinaryapi.queues.producers.AppointmentProducer;
import com.bodden.veterinaryapi.repositories.AppointmentRepository;
import com.bodden.veterinaryapi.repositories.PetRepository;
import com.bodden.veterinaryapi.services.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.util.Collection;

public class AppointmentServiceDefault implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AppointmentProducer appointmentProducer;

    public AppointmentServiceDefault() {
    }

    @Override
    public Appointment createAppointment(Long petId, Appointment appointment) {
        checkValidDate(appointment);
        return petRepository.findById(petId).map(pet -> {
            // create appointment
            appointment.setPet(pet);
            Appointment saved = appointmentRepository.save(appointment);
            handleHistory(saved, AppointmentHistory.LogType.CREATED);
            return saved;
        }).orElseThrow(() -> notFound(petId));
    }

    @Override
    public Appointment updateAppointment(Long id, Long petId, Appointment appointmentRequest) {
        checkValidDate(appointmentRequest);
        return appointmentRepository.findByIdAndPetId(id, petId).map(appointment -> {
            // check that the pet update exists in db
            if (!petRepository.existsById(appointment.getId())) {
                throw notFound(appointment.getPet().getId());
            }
            appointment.setLocalDate(appointmentRequest.getLocalDate());
            appointment.setLocalTime(appointmentRequest.getLocalTime());
            appointment.setService(appointmentRequest.getService());
            appointment.setPet(appointment.getPet());
            handleHistory(appointment, AppointmentHistory.LogType.UPDATED);
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> notFound(id, petId));
    }

    @Override
    public boolean deleteAppointment(Long id, Long petId) {
        return appointmentRepository.findByIdAndPetId(id, petId).map(appointment -> {
            appointmentRepository.delete(appointment);
            return true;
        }).orElseThrow(() -> notFound(id, petId));
    }

    @Override
    public Collection<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> notFound(id));
    }

    @Override
    public Collection<Appointment> appointmentByPet(Long id) {
        return appointmentRepository.findByPetId(id).orElseThrow(() -> notFound(id));
    }


    private void handleHistory(Appointment appointment, AppointmentHistory.LogType log) {
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setAppointment(appointment);
        appointmentHistory.setLog(log);
        appointmentHistory.setDate(LocalDateTime.now());
        appointmentProducer.sendAppointment(appointmentHistory);
    }

    private void checkValidDate(Appointment appointment) {
        java.time.LocalDate appDate = appointment.getLocalDate();
        java.time.LocalTime appTime = appointment.getLocalTime();
        java.time.LocalDateTime dt = LocalDateTime.of(appDate, appTime);
        java.time.LocalDateTime today = LocalDateTime.now();

        if (dt.isBefore(today)) {
            throw new InvalidDataException("Cannot create appointments in the past");
        }
    }

    private ResourceNotFoundException notFound(Long petId) {
        return new ResourceNotFoundException("PetId " + petId + " not found");
    }

    private ResourceNotFoundException notFound(Long id, Long petId) {
        return new ResourceNotFoundException("Appointment not found with id " + id + " and petId " + petId);
    }
}
