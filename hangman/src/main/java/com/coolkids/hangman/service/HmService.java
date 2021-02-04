package com.coolkids.hangman.service;

import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class HmService implements HmServiceInterface {

    @Override
    public Game createGame(Game game) {
        return null;
    }

    @Override
    public Round createRound(Round round) {
        return null;
    }

    @Override
    public Game findById(int id) {
        return null;
    }

    @Override
    public List<Game> allGames() {
        return null;
    }

    @Override
    public List<Round> findRoundById(int id) {
        return null;
    }
}
