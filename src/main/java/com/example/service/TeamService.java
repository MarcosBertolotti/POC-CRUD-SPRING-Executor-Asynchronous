package com.example.service;

import com.example.dto.TeamDTO;
import com.example.model.Team;
import com.example.model.projection.CantPlayersxTeam;
import com.example.repository.ICantPlayersxTeamRepository;
import com.example.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class TeamService {

    private static final String TEAM_NOT_FOUND = "Not Exists team with id: %s";

    @Autowired
    ITeamRepository teamRepository;

    @Autowired
    ICantPlayersxTeamRepository cantPlayersxTeamRepository;

    public void add(final Team team){
        teamRepository.save(team);
    }

    public void update(final Team team, final Integer id){
        Team teamOld = teamRepository.findById(id).
                orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(TEAM_NOT_FOUND,id)));

        if(!teamOld.equals(team))
            teamRepository.save(team);
    }

    public void deleteById(final Integer id){
        teamRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(TEAM_NOT_FOUND,id)));

        teamRepository.deleteById(id);
    }

    public List<Team> getAll(){
        return teamRepository.findAll();
    }

    public Team getById(final Integer id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(TEAM_NOT_FOUND,id)));
    }

    public TeamDTO getByName(final String name){
        return teamRepository.findByName(name);
    }

    public TeamDTO getByNameDinamico(String name){
        return teamRepository.findByName(name,TeamDTO.class);
    }

    public List<CantPlayersxTeam> getCantPlayersXteam(){
        return cantPlayersxTeamRepository.getCantPlayersxTeam();
    }
}
