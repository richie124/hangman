package com.coolkids.hangman.service;
import com.coolkids.hangman.models.*;

import java.util.List;

public interface HmServiceInterface {

    public Game createGame();

    public Round createRound(Round round);

    public Game findById(int id);

    public List<Game> allGames();

    public List<Round> findRoundById(int id);
}
