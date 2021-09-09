package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {}

