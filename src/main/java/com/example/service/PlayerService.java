package com.example.service;

import com.example.model.Player;
import com.example.model.Team;
import com.example.repository.IPlayerRepository;
import com.example.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class PlayerService {

    private static final String PLAYER_NOT_FOUND = "Not Exists person with id: %s";
    private static final String TEAM_NOT_FOUND = "Not Exists team with id: %s";

    @Autowired
    IPlayerRepository playerRepository;

    @Autowired
    ITeamRepository teamRepository;

    public void add(final Player player, final Integer idTeam){

        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(TEAM_NOT_FOUND,idTeam)));

        team.getPlayers().add(player);
        player.setTeam(team);

        playerRepository.save(player);
        teamRepository.save(team);
    }

    public List<Player> getAll(){

        return playerRepository.findAll();
    }

    public Player getById(final Integer id){

        return playerRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(PLAYER_NOT_FOUND,id)));
    }

    public void update(Player player, final Integer idTeam){

        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(TEAM_NOT_FOUND,idTeam)));

        team.getPlayers().add(player);
        player.setTeam(team);

        playerRepository.save(player);
        teamRepository.save(team);
    }

    public void deleteById(final Integer id){

        playerRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST,String.format(PLAYER_NOT_FOUND,id)));

        playerRepository.deleteById(id);
    }

}
