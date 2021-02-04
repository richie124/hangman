package com.coolkids.hangman.service;

import com.coolkids.hangman.data.Dao;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class HmService implements HmServiceInterface {

    private final Dao hmDao;

    public HmService(Dao hmDao) {
        this.hmDao = hmDao;
    }

    @Override
    public Game createGame() {
        Game game = new Game();
        return hmDao.start(game);
    }

    @Override
    public Round createRound(Round round) {

        // Find the answer to the user's game
        int gameId = round.getGameId();
        Game thisGame = hmDao.findGameById(gameId);
        String ans = thisGame.getAnswer();

        // Get the results of the user's guess compared to the answer
        String guess = round.getGuess();

        // Get the previous CurrentAnswer
        Round prevRound = hmDao.findRoundById(round.getId());
        String prevAnswer = prevRound.getCurrentAnswer();

        // create currAnswer array length of answer
        char[] ansArr = ans.toCharArray();

        // For each letter in the answer
        for ( int i = 0; i < ansArr.length; i++ ) {
            // If the letter is NOT in the guess, or in the previous answer, set it to an underscore
            if (guess.indexOf(ansArr[i]) < 0 || prevAnswer.indexOf(ansArr[i]) < 0) {
                ansArr[i] = '_';
            }
        }

        // Set the current answer to the string of the ansArr[]
        String currAns = String.valueOf(ansArr);
        round.setCurrentAnswer(currAns);


        // If the user guessed all right numbers in the right places, set the game status to completed (true)
//        if(guessResultSet[0] == 4) {
//            hmDao.finishGame(gameId);
//        }
        // Inserts a row into the rounds table
        return hmDao.guess(round);
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
