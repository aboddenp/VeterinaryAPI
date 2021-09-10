package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    Optional<Appointment> findById(Long id);
    Optional<Collection<Appointment>> findByPetId(Long petId);
    Optional<Appointment> findByIdAndPetId(Long id, Long petId);
}

