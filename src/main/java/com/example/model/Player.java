package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_player")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "name can not be null or empty")
    private String name;

    @Column(name = "sur_name")
    @NotEmpty(message = "surname can not be null or empty")
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

    @Column(name = "salary")
    @NotNull(message = "salary can not be null")
    @Positive(message = "salary must be positive")
    private Integer salary;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id_team")
    @JsonIgnore
    private Team team;

}
