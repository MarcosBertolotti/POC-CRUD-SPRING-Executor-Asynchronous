package com.example.repository;

import com.example.dto.TeamDTO;
import com.example.model.Team;
import com.example.model.projection.ITeamName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITeamRepository extends JpaRepository<Team,Integer> {

    @Query(value = "select * from teams where extract(year from foundation_date) = ?1",nativeQuery = true)
    List<ITeamName> findByFoundationAge(final Integer age);

    ITeamName findByName(final String name);

    //Team findByName(final String name);
}
