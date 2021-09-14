package com.bodden.veterinaryapi.services.interfaces;

import com.bodden.veterinaryapi.models.Appointment;

import java.util.Collection;

public interface AppointmentService {
    public abstract Appointment createAppointment(Long petId, Appointment appointment);

    public abstract Appointment updateAppointment(Long id, Long petId, Appointment appointmentRequest);

    public abstract boolean deleteAppointment(Long id, Long petId);

    public abstract Collection<Appointment> getAppointments();

    public abstract Appointment getAppointment(Long id);

    public abstract Collection<Appointment> appointmentByPet(Long id);
}
