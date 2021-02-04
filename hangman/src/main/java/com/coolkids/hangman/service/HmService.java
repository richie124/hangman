package com.coolkids.hangman.service;

import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class HmService implements HmServiceInterface {

    private final GamesDao gDao;
    private final RoundsDao rDao;

    public HangmanService(GamesDao gDao, RoundsDao rDao) {
        this.gDao = gDao;
        this.rDao = rDao;
    }

    @Override
    public Game createGame(Game game) {

        // Set a random answer for new game
        Random rand = new Random();
        String answer = "";
        while (answer.length() < 4){
            String randInt = String.valueOf(rand.nextInt(9));
            if (answer.indexOf(randInt) == -1){
                answer = answer + randInt;
            }
        }
        game.setAnswer(answer);

        // Add game to storage
        gDao.add(game);

        // set the answer to null so user cannot see it.
        game.setAnswer(null);

        return game;
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
