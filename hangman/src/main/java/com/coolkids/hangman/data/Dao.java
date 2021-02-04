package com.coolkids.hangman.data;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

public interface Dao {
    //create Game
    Game start(Game game);

    //make a guess
    Round guess(Game game, String guess);

    //get answer by game id
    String answer(Game game);





    //set wrong guess

    //set in progress - change game to be done
    //boolean


    //end game
    boolean finished(Game game);

    //find game with game id
    Game findById(int id);


}
