package com.example.repository;

import com.example.dto.TeamDTO;
import com.example.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends JpaRepository<Team,Integer> {

    // no me convierte de entidad a dto
    TeamDTO findByName(final String name);
    <T> T findByName(String name, Class<T> type);

}
