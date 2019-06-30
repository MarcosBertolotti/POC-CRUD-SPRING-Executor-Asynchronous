package com.example.service;

import com.example.exception.TeamNotFoundException;
import com.example.model.Team;
import com.example.model.projection.CantPlayersXTeam;
import com.example.model.projection.IPlayerName;
import com.example.model.projection.ITeamName;
import com.example.repository.ICantPlayersXTeamRepository;
import com.example.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;

@Service
public class TeamService {

    private static final String TEAM_NOT_FOUND_ID = "no existe team con id: %s";
    private static final String TEAM_NOT_FOUND_NAME = "no existe team con nombre: %s";
    private static final String TEAM_NOT_FOUND_FOUNDATION = "no existe team con año de fundacion: %s";

    @Autowired
    private ITeamRepository teamRepository;

    @Autowired
    private ICantPlayersXTeamRepository cantPlayersXTeam;

    public Team add(final Team team){

        return teamRepository.save(team);
    }

    public Team update(final Team team, final Integer id) throws HttpClientErrorException{

        teamRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(TEAM_NOT_FOUND_ID,id)));

        return teamRepository.save(team);
    }

    public Team deleteById(final Integer id) throws HttpClientErrorException{

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(TEAM_NOT_FOUND_ID,id)));

        teamRepository.deleteById(id);

        return team;
    }

    public List<Team> getAll(){

        return teamRepository.findAll();
    }

    public Team getById(final Integer id) throws HttpClientErrorException{

        return teamRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(TEAM_NOT_FOUND_ID,id)));
    }

    public List<ITeamName> getByFoundationDate(final Integer foundation) throws TeamNotFoundException{

        List<ITeamName> teams = teamRepository.findByFoundationDate(foundation);

        if(isNull(teams))
            throw new TeamNotFoundException(String.format(TEAM_NOT_FOUND_FOUNDATION,foundation));

        return teams;
    }

    public ITeamName getByName(final String name) throws TeamNotFoundException{

        ITeamName team = teamRepository.findByName(name);

        if(isNull(team))
            throw new TeamNotFoundException(String.format(TEAM_NOT_FOUND_NAME,name));

        return team;
    }

    public List<CantPlayersXTeam> getCantPlayersXTeam(){

        return cantPlayersXTeam.getCantPlayersXTeam();
    }

}
