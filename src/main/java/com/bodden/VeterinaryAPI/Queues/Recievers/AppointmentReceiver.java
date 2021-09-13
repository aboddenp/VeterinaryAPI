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
    @Autowired
    AppointmentRepository appointmentRepository;

    public void receiveAppointment(String appointmentData) {
        // Appointment data received from queue: appointmentID  HistoryType dateOfChange
        // Implement Serializable after testing with string data
        String[] data  = appointmentData.split("\\|");
        Long appId = Long.parseLong(data[0]);
        AppointmentHistory.LogType type = AppointmentHistory.LogType.valueOf(data[1]);
        LocalDateTime date = LocalDateTime.parse(data[2]);

        Appointment appointment = appointmentRepository.findById(appId)
                .orElse(null);
        if(appointment == null){
            return;
        }

        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentHistory.setAppointment(appointment);
        appointmentHistory.setDate(date);
        appointmentHistory.setLog(type);
        appointmentHistoryRepository.save(appointmentHistory);
    }
}
