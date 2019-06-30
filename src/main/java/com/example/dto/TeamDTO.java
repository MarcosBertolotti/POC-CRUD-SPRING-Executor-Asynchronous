package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TeamDTO {

    @Id
    private Integer id;

    @NotEmpty(message = "team name cannot be null or empty")
    @Size(min = 4, max = 20, message = "team name must be between 4 and 20 characters")
    private String name;

    @NotNull(message = "Foundation date can not be null")
    @PastOrPresent(message = "Foundation date must be past or present date")
    private LocalDate foundationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "team")
    private List<PlayerDTO> players;
}
