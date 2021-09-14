package com.bodden.veterinaryapi.repositories;

import com.bodden.veterinaryapi.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Long> {}
