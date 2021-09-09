package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {}
