package com.bodden.veterinaryapi.repositories;

import com.bodden.veterinaryapi.models.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory,Long> {
    public Collection<AppointmentHistory> findByAppointmentId(Long appointmentId);
}
