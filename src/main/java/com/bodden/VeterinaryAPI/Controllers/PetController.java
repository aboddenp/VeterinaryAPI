package com.bodden.VeterinaryAPI.Controllers;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Pet;
import com.bodden.VeterinaryAPI.Repositories.OwnerRepository;
import com.bodden.VeterinaryAPI.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/pets")
    public Collection<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @GetMapping("/pets/{petId}")
    public Pet getPet(@PathVariable(value = "petId") Long petId) {
        return petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("PetId " + petId + " not found"));
    }

    @GetMapping("/owners/{ownerId}/pets")
    public Collection<Pet> getAllPetsByOwnerId(@PathVariable(value = "ownerId") Long ownerId) {
        return petRepository.findByOwnerId(ownerId).orElseThrow(()-> new ResourceNotFoundException(("ownerId"+ ownerId + "not found")));
    }

    @PostMapping("/owners/{ownerId}/pets")
    public Pet createPet(@PathVariable(value = "ownerId") Long ownerId,@RequestBody Pet pet) {
        return ownerRepository.findById(ownerId).map(owner -> {
                    pet.setOwner(owner);
                    return petRepository.save(pet);
        }
        ).orElseThrow(()-> new ResourceNotFoundException("ownerId"+ ownerId + "not found"));
    }

    @PutMapping("/owners/{ownerId}/pets/{petId}")
    public Pet updatePet(@PathVariable Long ownerId,@PathVariable Long petId,@RequestBody Pet petRequest) {

        return petRepository.findByIdAndOwnerId(petId,ownerId).map(pet -> {
            pet.setName(petRequest.getName());
            pet.setType(petRequest.getType());
            pet.setOwner(petRequest.getOwner());
            return petRepository.save(pet);
        }).orElseThrow(() -> new ResourceNotFoundException("Owner not found with id " + ownerId + " and petId " + petId));
    }

    @DeleteMapping("/owners/{ownerId}/pets/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId,@PathVariable Long ownerId) {
        return petRepository.findByIdAndOwnerId(petId,ownerId).map(pet -> {
            petRepository.delete(pet);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Owner not found with id " + ownerId + " and petId " + petId));
    }

}