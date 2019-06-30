package com.example.repository;

import com.example.model.projection.CantPlayersXTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICantPlayersXTeamRepository extends JpaRepository<CantPlayersXTeam,String> {

    @Query(value = "select t.name, count(p.player_id) as cantidad " +
            "from teams t " +
            "inner join players p on p.team_id = t.team_id " +
            "group by t.team_id ", nativeQuery = true)
    List<CantPlayersXTeam> getCantPlayersXTeam();

    //jpql
}
