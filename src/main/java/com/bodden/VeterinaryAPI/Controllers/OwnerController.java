package com.bodden.VeterinaryAPI.Controllers;

import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Owner;
import com.bodden.VeterinaryAPI.Repositories.OwnerRepository;
import com.bodden.VeterinaryAPI.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/owners")
    public List<Owner> getAllPets() {
        return ownerRepository.findAll();
    }

    @PostMapping("/owners")
    public Owner createPet(@RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @PutMapping("/owners/{ownerId}")
    public Owner updatePet(@PathVariable Long ownerId,@RequestBody Owner ownerRequest) {
        return ownerRepository.findById(ownerId).map(pet -> {
            pet.setName(ownerRequest.getName());
            pet.setLastName(ownerRequest.getLastName());
            return ownerRepository.save(pet);
        }).orElseThrow(() -> new ResourceNotFoundException("ownerId " + ownerId + " not found"));
    }


    @DeleteMapping("/owners/{ownerId}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long ownerId) {
        return ownerRepository.findById(ownerId).map(owner -> {
            ownerRepository.delete(owner);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ownerId " + ownerId + " not found"));
    }

}