package com.bodden.veterinaryapi.controllers;

import com.bodden.veterinaryapi.exceptions.ResourceNotFoundException;
import com.bodden.veterinaryapi.models.Owner;
import com.bodden.veterinaryapi.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/owners")
    public Collection<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @GetMapping("/owners/{ownerId}")
    public Owner getOwnerById(@PathVariable Long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException());
    }

    @PostMapping("/owners")
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @PutMapping("/owners/{ownerId}")
    public Owner updateOwner(@PathVariable Long ownerId, @RequestBody Owner ownerRequest) {
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