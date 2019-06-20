package com.example.dto;

import com.example.model.Player;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TeamDTO {

    @Id
    private Integer id;

    @NotEmpty(message = "team name cannot be null or empty")
    @Size(min = 4, max = 20, message = "team name must be between 4 and 20 characters")
    private String name;

    @NotNull(message = "Foundation date can not be null")
    @PastOrPresent(message = "Foundation date must be past or present date")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate foundationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "team")
    private List<PlayerDTO> players;
}
