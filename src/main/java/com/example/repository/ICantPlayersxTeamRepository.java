package com.example.repository;

import com.example.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICantPlayersxTeamRepository<CantPlayersxTeam> extends JpaRepository<Team,Integer> {

    @Query(value = "select t.name, count(p.player_id) as cantidad" +
            "from teams t " +
            "inner join players p on p.team_id = t.team_id " +
            "group by t.name", nativeQuery = true)
    List<CantPlayersxTeam> getCantPlayersxTeam();
}
