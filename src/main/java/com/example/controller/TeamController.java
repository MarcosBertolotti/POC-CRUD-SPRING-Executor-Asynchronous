package com.example.controller;

import com.example.model.Team;
import com.example.dto.TeamDTO;
import com.example.model.projection.CantPlayersXTeam;
import com.example.model.projection.ITeamName;
import com.example.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<Team> add(@RequestBody final Team team){
        try {
            Team t = teamService.add(team);

            return ResponseEntity.created(URI.create("http://localhost:8080/teams/" + t.getId())).body(t);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody final Team team, @PathVariable("id") final Integer id){
        try {
            Team t = teamService.update(team, id);

            return ResponseEntity.created(URI.create("http://localhost:8080/teams/" + t.getId())).body(t);

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final Integer id){
        try {
            Team team = teamService.deleteById(id);

            return ResponseEntity.ok(team);

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<TeamDTO>> getAll(){
        try{
            List<Team> teams = teamService.getAll();

            if(teams.size() > 0) {

                List<TeamDTO> teamsDto = teams.stream()
                        .map(t -> converEntityToDto(t))
                        .collect(Collectors.toList());

                return ResponseEntity.ok(teamsDto);
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Integer id){
        try{
            Team team = teamService.getById(id);

            return ResponseEntity.ok(converEntityToDto(team));

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("foundation/{foundation}")
    public ResponseEntity<?> getByFoundationDate(@PathVariable("foundation") final Integer foundation) {
        try {
            List<ITeamName> teams = teamService.getByFoundationDate(foundation);

            if(teams.size() > 0)
                return ResponseEntity.ok(teams);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") final String name){
        try{
            ITeamName team = teamService.getByName(name);

            return ResponseEntity.ok(team);

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("players/cantidad")
    public ResponseEntity<List<CantPlayersXTeam>> getCantPlayersXTeam(){
        try{
            List<CantPlayersXTeam> teams = teamService.getCantPlayersXTeam();

            if(teams.size() > 0)
                return ResponseEntity.ok(teams);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    private TeamDTO converEntityToDto(Team team){
        return modelMapper.map(team, TeamDTO.class);
    }

    private Team converDtoToEntity(TeamDTO teamDto){

        Team team = modelMapper.map(teamDto, Team.class);

        Integer id = teamDto.getId();

        if(!isNull(id)){
            Team teamOld = teamService.getById(id);
            team.setMoney(teamOld.getMoney());
        }
        return team;
    }
}















