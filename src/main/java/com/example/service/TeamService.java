package com.example.service;

import com.example.model.Team;
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

    public void add(final Team team){
        teamRepository.save(team);
    }

    public List<Team> getAll(){
        return teamRepository.findAll();
    }

    public Team getById(final Integer id){
        return teamRepository.findById(id)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(TEAM_NOT_FOUND,id)));
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
}
