package com.example.institutionapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INSTITUTION")
public class Institution {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 5)
    private Integer code;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 1)
    private Integer status;
}
