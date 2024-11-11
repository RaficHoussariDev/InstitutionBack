package com.example.institutionapp.repositories;

import com.example.institutionapp.entities.InstitutionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionUserRepository extends JpaRepository<InstitutionUser, Integer> {
    Optional<InstitutionUser> findByUsername(String username);
}
