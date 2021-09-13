package com.bodden.VeterinaryAPI.Queues.Recievers;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Models.AppointmentHistory;
import com.bodden.VeterinaryAPI.Repositories.AppointmentHistoryRepository;
import com.bodden.VeterinaryAPI.Repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentReceiver {

    @Autowired
    AppointmentHistoryRepository appointmentHistoryRepository;

    public void receiveAppointment(AppointmentHistory appointmentHistory) {
        appointmentHistoryRepository.save(appointmentHistory);
    }
}
