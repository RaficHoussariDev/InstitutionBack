package com.example.institutionapp.repositories;

import com.example.institutionapp.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
    @Query("Select i from Institution i where i.status = 1")
    List<Institution> findActiveInstitutions();
}
