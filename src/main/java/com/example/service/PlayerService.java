package com.example.service;

import com.example.exception.PlayerNotFoundException;
import com.example.model.Player;
import com.example.model.Team;
import com.example.model.projection.IPlayerName;
import com.example.repository.IPlayerRepository;
import com.example.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PlayerService {

    private static final String TEAM_NOT_FOUND = "no existe team con id: %s";
    private static final String PLAYER_NOT_FOUND = "no existe player con id: %s";
    private static final String PLAYER_NOT_FOUND_AGE = "no existe player con edad: %s";
    private static final String PLAYER_NOT_FOUND_NAME = "no existe player con nombre: %s";

    @Autowired
    private IPlayerRepository playerRepository;

    @Autowired
    private ITeamRepository teamRepository;

    public Player add(final Player player, final Integer teamId){

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(TEAM_NOT_FOUND,teamId)));

        team.getPlayers().add(player);
        player.setTeam(team);

        return playerRepository.save(player);
    }

    public Player update(final Player player, final Integer id, final Integer idTeam) throws HttpClientErrorException{

        Player playerOld = playerRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(PLAYER_NOT_FOUND,id)));

        if(idTeam != playerOld.getTeam().getId()){

            Team team = teamRepository.findById(idTeam)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(TEAM_NOT_FOUND,idTeam)));

            team.getPlayers().add(player);
            player.setTeam(team);
        }
        return playerRepository.save(player);
    }

    public Player deleteById(final Integer id) throws HttpClientErrorException{

        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(PLAYER_NOT_FOUND,id)));

        teamRepository.deleteById(id);

        return player;
    }

    public List<Player> getAll(){

        return playerRepository.findAll();
    }

    public Player getById(final Integer id) throws HttpClientErrorException{

        return playerRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(PLAYER_NOT_FOUND,id)));
    }

    public double getAverageAge(){

        return playerRepository.getAverageAge();
    }

    public List<Player> getByAge(final Integer age) throws PlayerNotFoundException{

        List<Player> players =  playerRepository.getByAge(age);

        if(isNull(players))
            throw new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_AGE,age));

        return players;
    }

    public List<IPlayerName> getNamesByAge(final Integer age) throws PlayerNotFoundException{

        List<IPlayerName> players = playerRepository.getNamesByAge(age);

        if(isNull(players))
            throw new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_AGE,age));

        return players;
    }

    public List<IPlayerName> getNamesByName(final String name) throws PlayerNotFoundException{

        List<IPlayerName> players = playerRepository.getNamesByName(name);

        if(isNull(players))
            throw new PlayerNotFoundException(String.format(PLAYER_NOT_FOUND_NAME,name));

        return players;
    }

}
