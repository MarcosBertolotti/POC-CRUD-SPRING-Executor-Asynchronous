package com.example.controller;

import com.example.model.Player;
import com.example.dto.PlayerDTO;
import com.example.model.projection.IPlayerName;
import com.example.service.PlayerService;
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
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/team/{id}")
    public void add(@RequestBody final Player player, @PathVariable("id") final Integer idTeam){

        playerService.add(player,idTeam);
    }

    @PutMapping("/{idPlayer}/team/{idTeam}")
    public void update(@RequestBody Player player, @PathVariable("idPlayer") final Integer idPlayer, @PathVariable("idTeam") final Integer idTeam){

        playerService.update(player,idPlayer,idTeam);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") final Integer id){

        playerService.deleteById(id);
    }

    @GetMapping("")
    public List<PlayerDTO> getAll(){

        List<Player> players = playerService.getAll();

        return players.stream()
                .map(player -> convertEntityToDto(player))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlayerDTO getById(@PathVariable("id") final Integer id){

        Player player = playerService.getById(id);

        return convertEntityToDto(player);
    }

    @GetMapping("/team/{id}")
    public List<PlayerDTO> getByTeamId(@PathVariable("id") final Integer teamId){

        List<Player> players = playerService.getByTeamId(teamId);

        return players.stream()
                .map(player -> convertEntityToDto(player))
                .collect(Collectors.toList());
    }

    @GetMapping("/age/avg")
    public double getAverageAge(){
        return playerService.getAverageAge();
    }

    @GetMapping("/age/max/{name}")
    public int getMaxAgeMinus(@PathVariable("name") final String name){
        return playerService.getMaxAgeMinus(name);
    }

    @GetMapping("/age/{age}")
    public List<PlayerDTO> getByAge(@PathVariable("age") final Integer age){

        List<Player> players = playerService.getByAge(age);

        return players.stream()
                .map(p -> convertEntityToDto(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/name/age/{age}")
    public List<IPlayerName> getNamesByAge(@PathVariable("age") final Integer age) {
        return playerService.getNamesByAge(age);
    }











    private PlayerDTO convertEntityToDto(Player player)  {

        return modelMapper.map(player, PlayerDTO.class);
    }

    private Player convertDtoToEntity(PlayerDTO playerDto){

        Player player = modelMapper.map(playerDto,Player.class);

        if(!isNull(playerDto.getId())){
            Player oldPlayer = playerService.getById(playerDto.getId());
            player.setSalary(oldPlayer.getSalary());
        }
        return player;
    }
}

