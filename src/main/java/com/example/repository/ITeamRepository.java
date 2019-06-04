package com.example.repository;

import com.example.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface ITeamRepository extends JpaRepository<@NotBlank Team,@NotBlank Integer> {
}
