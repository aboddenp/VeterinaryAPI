package com.bodden.VeterinaryAPI.Controllers;

import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Models.AppointmentHistory;
import com.bodden.VeterinaryAPI.Repositories.AppointmentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AppointmentHistoryController {

    @Autowired
    private AppointmentHistoryRepository appointmentHistoryRepository;

    @GetMapping("appointments/{appId}/history")
    public Collection<AppointmentHistory> getHistory(@PathVariable(value = "appId") Long appId ) {
        return appointmentHistoryRepository.findByAppointmentId(appId);
    }

}
