package com.example.controller;


import com.example.dto.TeamDTO;
import com.example.model.Team;
import com.example.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("")
    public void add(@RequestBody final Team team){
        teamService.add(team);
    }

    @GetMapping("")
    public List<TeamDTO> getAll(){

        List<Team> teams = teamService.getAll();

        return teams.stream()
                .map(team -> convertEntityToDTO(team))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamDTO getById(@PathVariable("id") final Integer id){
        return convertEntityToDTO(teamService.getById(id));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody final Team team, @PathVariable("id") final Integer id){
        teamService.update(team,id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") final Integer id){
        teamService.deleteById(id);
    }

    private TeamDTO convertEntityToDTO(Team team){
        return modelMapper.map(team, TeamDTO.class);
    }

    private Team convertDTOtoEntity(TeamDTO teamDto){

        Team team = modelMapper.map(teamDto, Team.class);

        Integer id = team.getId();

        if(!isNull(id)){
            Team teamOld = teamService.getById(id);
            team.setMoney(teamOld.getMoney());
        }
        return team;
    }

}
