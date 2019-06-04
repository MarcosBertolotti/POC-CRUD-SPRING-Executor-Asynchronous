package com.example.controller;


import com.example.model.Team;
import com.example.service.TeamService;
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

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping("")
    public void add(@RequestBody final Team team){
        teamService.add(team);
    }

    @GetMapping("")
    public List<Team> getAll(){
        return teamService.getAll();
    }

    @GetMapping("/{id}")
    public Team getById(@PathVariable("id") final Integer id){
        return teamService.getById(id);
    }

    @PutMapping("")
    public void update(@RequestBody final Team team){
        teamService.update(team);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") final Integer id){
        teamService.deleteById(id);
    }
}
