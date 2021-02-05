package com.coolkids.hangman.service;

import com.coolkids.hangman.data.Dao;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

import org.springframework.stereotype.Service;

import java.util.List;

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
        String guess = round.getGuess().toLowerCase();

        String prevGuess = "";
        String currAns = "";

        // Get the previous CurrentAnswer
        if( hmDao.getRoundCountByGameId(gameId) > 0 ) {
            Round prevRound = hmDao.findPrevRoundByGameId(gameId);
            prevGuess = prevRound.getCurrentAnswer();
        }

        // If guess is longer than 1, compare guess directly with answer
        // Otherwise check if letter guess in answer
        if ( guess.length() > 1 ) {
            // Compare with ans (make sure all lowercase)

            boolean isWin = ans.equals( guess );

            if (isWin) {
                currAns = "You Win! Answer was: " + ans;
            } else {
                currAns = "You Lose. Answer was: " + ans;
            }
            checkEndGame(thisGame, guess, ans);

        } else if ( ans.indexOf( guess.charAt(0) ) < 0 ) {// if (if the first letter in guess is NOT in the answer), wrong guess

            int prevWrongGuesses = thisGame.getWrongGuess();
            thisGame.setWrongGuess(prevWrongGuesses+1);
            if ( thisGame.getWrongGuess() >= 6 ) {
                thisGame = checkEndGame(thisGame, guess, ans);
                currAns = "You Lose. Answer was: " + ans;
            } else {
                currAns = prevGuess;
            }

        } else {// If a single letter guess, and guess is in answer, go through loop
            // create currAnswer array length of answer
            char[] ansArr = ans.toCharArray();
            char[] finalAnsArr = new char[ansArr.length*2];

            // For each letter in the ansArray
            for ( int i = 0; i < ansArr.length; i++ ) {
                int ansLetter = i*2;
                finalAnsArr[ansLetter] = ansArr[i];

                // If char in the answer iw a space, leave it alone and continue with next char
                // Else if the letter is NOT in the guess, or in the previous answer, set it to an underscore
                if(ansArr[i] == ' '){
                    continue;
                } else if (guess.indexOf(ansArr[i]) < 0 && prevGuess.indexOf(ansArr[i]) < 0) { // if (a is not in guess && a is not in prevGuess)
                    int blank = i*2;
                    finalAnsArr[blank] = '_';
                }
                int space = (i*2)+1;
                finalAnsArr[space] = ' ';
            }
            currAns = String.valueOf(finalAnsArr);

            // Set the current answer to the string of the ansArr[]
            boolean isWin = currAns.equals( ans );
            if (isWin) {
                thisGame = checkEndGame(thisGame, currAns, ans);
                currAns = "You Win! Answer was: " + ans;
            }
        }



        round.setCurrentAnswer(currAns);
        hmDao.updateGame(thisGame);

        return hmDao.guess(round);
    }


    private Game checkEndGame(Game game, String guess, String answer){

        // If WIN
        if ( guess.equals( answer.toLowerCase() ) || game.getWrongGuess() >= 6) {
            // Set inProgress to false
            game.setInProgress(false);
            return hmDao.updateGame(game);
        }

        return game;
    }

    @Override
    public Game findById(int id) {
        return hmDao.findGameById(id);
    }

    @Override
    public List<Game> allGames() {
        return null;
    }

    @Override
    public List<Round> findRoundById(int id) {
        return hmDao.findRoundByGameId(id);
    }
}
