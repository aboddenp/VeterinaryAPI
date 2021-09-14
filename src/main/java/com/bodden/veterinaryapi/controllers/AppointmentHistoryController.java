package com.bodden.veterinaryapi.controllers;

import com.bodden.veterinaryapi.models.AppointmentHistory;
import com.bodden.veterinaryapi.repositories.AppointmentHistoryRepository;
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
    public Collection<AppointmentHistory> getHistory(@PathVariable(value = "appId") Long appId) {
        return appointmentHistoryRepository.findByAppointmentId(appId);
    }
}
