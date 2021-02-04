package com.coolkids.hangman.data;

import com.coolkids.hangman.data.Dao;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameDBdao implements Dao {

    private final JdbcTemplate jdbcTemplate;

    public GameDBdao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game start(Game game) {



        return null;
    }

    @Override
    public Round guess(int GameID, String guess) {
        return null;
    }

    @Override
    public String answer(Game game) {
        return null;
    }

    @Override
    public boolean finished(Game game) {
        return false;
    }
}
