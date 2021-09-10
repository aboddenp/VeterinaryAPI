package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    Optional<Collection<Pet>> findByOwnerId(Long ownerId);
    Optional<Pet> findByIdAndOwnerId(Long id, Long ownerId);
}
