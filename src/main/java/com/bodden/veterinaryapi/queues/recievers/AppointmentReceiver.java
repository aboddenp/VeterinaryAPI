package com.bodden.veterinaryapi.queues.recievers;

import com.bodden.veterinaryapi.models.AppointmentHistory;
import com.bodden.veterinaryapi.repositories.AppointmentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentReceiver {

    @Autowired
    AppointmentHistoryRepository appointmentHistoryRepository;

    public void receiveAppointment(AppointmentHistory appointmentHistory) {
        appointmentHistoryRepository.save(appointmentHistory);
    }
}
