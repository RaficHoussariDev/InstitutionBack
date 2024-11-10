package com.example.institutionapp.controllers;

import com.example.institutionapp.models.GetInstitutionResponse;
import com.example.institutionapp.models.UpsertInstitutionRequest;
import com.example.institutionapp.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/institution")
public class InstitutionController {
    private final InstitutionService institutionService;

    @GetMapping
    public ResponseEntity<List<GetInstitutionResponse>> getInstitutions() {
        return new ResponseEntity<>(
                this.institutionService.getInstitutions(),
                HttpStatus.OK
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<GetInstitutionResponse>> getInstitutionsActive() {
        return new ResponseEntity<>(
                this.institutionService.getActiveInstitutions(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Void> upsertInstitution(@RequestBody @Valid UpsertInstitutionRequest institution) {
        this.institutionService.upsertInstitution(institution);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Integer id) {
        this.institutionService.deleteInstitution(id);

        return ResponseEntity.noContent().build();
    }
}
