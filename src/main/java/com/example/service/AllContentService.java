package com.example.service;

import com.example.model.Player;
import com.example.model.Team;
import com.example.repository.IPlayerRepository;
import com.example.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AllContentService {

    @Autowired
    IPlayerRepository playerRepository;

    @Autowired
    ITeamRepository teamRepository;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Team>> getAllTeamNameAsync() {

        List<Team> teams = new ArrayList<>();

        try {
            teams = teamRepository.findAll();

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return CompletableFuture
                .completedFuture(teams);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<Player>> getAllPlayerNameAsync(){

        List<Player> players = new ArrayList<>();

        try {
            players = playerRepository.findAll();

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return CompletableFuture
                .completedFuture(players);
    }
}
