package com.safebank.demo.domains;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "tbCostumer")
public class Costumer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank private String name;

   
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank private String cpf;

   
    @Column(nullable = false, length = 20)
    @NotBlank private String phone;

    // @Column(nullable = false)
    // @NotBlank private String password;

}