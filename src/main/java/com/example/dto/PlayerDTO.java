package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players_dto")
public class PlayerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_player")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "name can not be null or empty")
    private String name;

    @Column(name = "sur_name")
    @NotEmpty(message = "subname can not be null or empty")
    private String surName;

    @Column(name = "dorsal")
    @NotNull(message = "dorsal can not be null")
    @Positive(message = "dorsal must be greater than or equal to 1")
    @Max(value = 99, message = "dorsal must be less than 99 ")
    private Integer dorsal;

    @Column(name = "age")
    @NotNull(message = "age can not be null")
    @Min(value = 18, message = "Player must be over 18 years old")
    private Integer age;

}
