package com.coolkids.hangman.data;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

import java.util.List;

public interface Dao {
    //create Game
    Game start(Game game);

    //make a guess
    Round guess(Round round);

    //find game with game id
    Game findGameById(int id);

    //find round by id
    Round findRoundById(int id);

    //update for game status - inProgress
    Game updateGame(Game game);

    Round findPrevRoundByGameId(int gameId);

    Integer getRoundCountByGameId(int gameId);

    List<Round> findRoundByGameId(int id);
}
