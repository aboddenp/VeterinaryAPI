package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory,Long> {
    public Collection<AppointmentHistory> findByAppointmentId(Long appointmentId);
}
