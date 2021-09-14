package com.bodden.veterinaryapi.repositories;

import com.bodden.veterinaryapi.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    Optional<Appointment> findById(Long id);
    Optional<Collection<Appointment>> findByPetId(Long petId);
    Optional<Appointment> findByIdAndPetId(Long id, Long petId);
}

