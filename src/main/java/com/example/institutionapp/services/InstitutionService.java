package com.example.institutionapp.services;

import com.example.institutionapp.entities.Institution;
import com.example.institutionapp.exceptions.InstitutionNotFoundException;
import com.example.institutionapp.models.GetInstitutionResponse;
import com.example.institutionapp.models.UpsertInstitutionRequest;
import com.example.institutionapp.repositories.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    public List<GetInstitutionResponse> getInstitutions() {
        log.info("fetching institutions");

        return this.institutionRepository
                .findAll()
                .stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toList());
    }

    public List<GetInstitutionResponse> getActiveInstitutions() {
        log.info("fetching active institutions");

        return this.institutionRepository
                .findActiveInstitutions()
                .stream()
                .map(this::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void upsertInstitution(UpsertInstitutionRequest institutionRequest) {
        if(institutionRequest.getId() == 0) {
            log.info("Creating new institution");
        } else {
            log.info("Updating institution with id: {}", institutionRequest.getId());

            if(!institutionRepository.existsById(institutionRequest.getId())) {
                throw new InstitutionNotFoundException("Institution with id " + institutionRequest.getId() + " not found");
            }
        }

        Institution institution = this.mapFromUpsertRequest(institutionRequest);
        this.institutionRepository.save(institution);
    }

    @Transactional
    public void deleteInstitution(Integer id) {
        log.info("Deleting institution with id: {}", id);

        Institution institution = this.institutionRepository
                .findById(id)
                .orElseThrow(() ->
                        new InstitutionNotFoundException("Institution with id " + id + " not found")
                );

        this.institutionRepository.delete(institution);
    }

    private GetInstitutionResponse mapFromEntity(Institution institution) {
        return new GetInstitutionResponse(
                institution.getId(),
                institution.getCode(),
                institution.getName(),
                institution.getStatus() == 1
        );
    }

    private Institution mapFromUpsertRequest(UpsertInstitutionRequest institution) {
        return new Institution(
                institution.getId(),
                institution.getCode(),
                institution.getName(),
                institution.getIsActive() ? 1 : 0
        );
    }
}
