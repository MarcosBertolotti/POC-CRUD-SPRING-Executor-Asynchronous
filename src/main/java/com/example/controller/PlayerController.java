package com.example.controller;

import com.example.model.Player;
import com.example.dto.PlayerDTO;
import com.example.model.projection.IPlayerName;
import com.example.service.PlayerService;
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
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/team/{id}")
    public ResponseEntity<Player> add(@RequestBody final Player player, @PathVariable("id") final Integer teamId){
        try {
            Player p = playerService.add(player,teamId);

            return ResponseEntity.created(URI.create("http://localhost:8080/teams/" + p.getId())).body(p);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/team/{idTeam}")
    public ResponseEntity<?> update(@RequestBody final Player player, @PathVariable("id") final Integer id, @PathVariable("idTeam")final Integer idTeam){
        try {
            Player p = playerService.update(player, id, idTeam);

            return ResponseEntity.accepted().body(p);

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // No funciona, borra todos los jugadores del equipo junto al equipo, (revisar model)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") final Integer id){
        try {
            Player player = playerService.deleteById(id);

            return ResponseEntity.ok(player);

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerDTO>> getAll(){
        try{
            List<Player> players = playerService.getAll();

            if(players.size() > 0) {

                List<PlayerDTO> playersDto = players.stream()
                        .map(p -> converEntityToDto(p))
                        .collect(Collectors.toList());

                return ResponseEntity.ok(playersDto);
            }
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") final Integer id){
        try{
            Player player = playerService.getById(id);

            return ResponseEntity.ok(converEntityToDto(player));

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/avgAge")
    public ResponseEntity<?> getAverageAge(){
        try{
            double avgAge = playerService.getAverageAge();

            return ResponseEntity.ok(avgAge);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("age/{age}")
    public ResponseEntity<?> getByAge(@PathVariable("age") final Integer age){
        try{
            List<Player> players = playerService.getByAge(age);

            if(players.size() > 0)
                return ResponseEntity.ok(players);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/age/{age}")
    public ResponseEntity<?> getNamesByAge(@PathVariable("age") final Integer age){
        try{
            List<IPlayerName> players = playerService.getNamesByAge(age);

            if(players.size() > 0)
                return ResponseEntity.ok(players);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}

        catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getNamesByName(@PathVariable("name") final String name){
        try{
            List<IPlayerName> players = playerService.getNamesByName(name);

            if(players.size() > 0)
                return ResponseEntity.ok(players);
            else
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (HttpClientErrorException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getLocalizedMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private PlayerDTO converEntityToDto(Player player){

        return modelMapper.map(player, PlayerDTO.class);
    }

    private Player converDtoToEntity(PlayerDTO playerDto){

        Player player = modelMapper.map(playerDto, Player.class);

        Integer id = playerDto.getId();

        if(!isNull(id)){
            Player playerOld = playerService.getById(id);
            player.setSalary(playerOld.getSalary());
        }
        return player;
    }

}
