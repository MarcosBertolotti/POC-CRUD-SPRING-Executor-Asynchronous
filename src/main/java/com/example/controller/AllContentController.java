package com.example.controller;

import com.example.model.AllContent;
import com.example.model.Player;
import com.example.model.Team;
import com.example.service.AllContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("allContent/")
public class AllContentController {

    @Autowired
    AllContentService allContentService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){

        try{
            CompletableFuture<List<Team>> teams = allContentService.getAllTeamNameAsync();
            CompletableFuture<List<Player>> players = allContentService.getAllPlayerNameAsync();

            AllContent allContent = AllContent.builder()
                    .teams(teams.join())
                    .players(players.join())
                    .build();

            return ResponseEntity.ok(allContent);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
