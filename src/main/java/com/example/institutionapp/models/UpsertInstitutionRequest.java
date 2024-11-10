package com.example.institutionapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertInstitutionRequest {
    private Integer id;

    @NotNull(message = "Code cannot be empty")
    @Min(value = 10000, message = "Code must be made of 5 digits")
    @Max(value = 99999, message = "Code must be made of 5 digits")
    private Integer code;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @NotNull(message = "status cannot be null")
    private Boolean isActive;
}
