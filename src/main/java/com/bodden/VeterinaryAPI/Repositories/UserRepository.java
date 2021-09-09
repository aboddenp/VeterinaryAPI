package com.bodden.VeterinaryAPI.Repositories;

import com.bodden.VeterinaryAPI.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {}
