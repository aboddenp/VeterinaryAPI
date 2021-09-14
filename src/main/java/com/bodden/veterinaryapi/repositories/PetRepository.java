package com.bodden.veterinaryapi.repositories;

import com.bodden.veterinaryapi.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    Optional<Collection<Pet>> findByOwnerId(Long ownerId);
    Optional<Pet> findByIdAndOwnerId(Long id, Long ownerId);
}
