package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PlayerDTO {

    @Id
    private Integer id;

    @NotEmpty(message = "name can not be null or empty")
    private String name;

    @NotEmpty(message = "surname can not be null or empty")
    private String surname;

    @NotNull(message = "dorsal can not be null")
    @Positive(message = "dorsal must be greater than or equal to 1")
    @Max(value = 99, message = "dorsal must be less than 99 ")
    private Integer dorsal;

    @NotNull(message = "age can not be null")
    @Min(value = 18, message = "Player must be over 18 years old")
    private Integer age;

}