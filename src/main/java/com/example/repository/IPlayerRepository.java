package com.example.repository;

import com.example.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface IPlayerRepository extends JpaRepository<@NotBlank Player, @NotBlank Integer> {
}
