package com.example.institutionapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInstitutionResponse {
    private Integer id;
    private Integer code;
    private String name;
    private Boolean isActive;
}
