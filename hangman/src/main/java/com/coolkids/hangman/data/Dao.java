package com.coolkids.hangman.data;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

public interface Dao {
    //create Game
    Game start(Game game);

    //make a guess
    Round guess(int GameID, String guess);

    //get answer by game id
    String answer(Game game);

    //end game
    boolean finished(Game game);


}
