package com.bodden.VeterinaryAPI.Controllers;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Pet;
import com.bodden.VeterinaryAPI.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @PostMapping("/pets")
    public Pet createPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PutMapping("/pets/{petId}")
    public Pet updatePet(@PathVariable Long petId,@RequestBody Pet petRequest) {
        return petRepository.findById(petId).map(pet -> {
            pet.setName(petRequest.getName());
            pet.setType(petRequest.getType());
            pet.setOwner(petRequest.getOwner());
            return petRepository.save(pet);
        }).orElseThrow(() -> new ResourceNotFoundException("PetId " + petId + " not found"));
    }


    @DeleteMapping("/pets/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId) {
        return petRepository.findById(petId).map(pet -> {
            petRepository.delete(pet);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PetId " + petId + " not found"));
    }

}