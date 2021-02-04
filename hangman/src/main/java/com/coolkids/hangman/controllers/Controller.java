package com.coolkids.hangman.controllers;

import com.coolkids.hangman.models.*;
import com.coolkids.hangman.service.HmServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hangman")
public class Controller {

    private final HmServiceInterface serviceInterface;

    public Controller(HmServiceInterface serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

//    @PostMapping("/begin")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Game createGame(@RequestBody Game game) {
//        return serviceInterface.createGame(game);
//    }
//
//    // Sends user's guess, returns round
//    @PostMapping("/guess")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Round createRound(@RequestBody Round round) {
//        return serviceInterface.createRound(round);
//    }
//
//    // Returns a specific game based on ID
//    @GetMapping("/game/{id}")
//    public Game findById(@PathVariable int id) {
//        return serviceInterface.findById(id);
//    }
//
//    // Returns a list of all games, with answer nulled if game status is not true (completed)
//    @GetMapping("/game")
//    public List<Game> all() {
//        return serviceInterface.allGames();
//    }
//
//    // Returns a list of rounds for the specified game, sorted by time
//    @GetMapping("/rounds/{id}")
//    public List<Round> findRoundById(@PathVariable int id) {
//        return serviceInterface.findRoundById(id);
//    }

    // Returns a specific game based on ID
    @GetMapping()
    public String Welcome() {
        return "Welcome to Hangman";
    }

}
