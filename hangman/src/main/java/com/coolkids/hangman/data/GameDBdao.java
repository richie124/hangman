package com.coolkids.hangman.data;

import com.coolkids.hangman.data.Dao;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class GameDBdao implements Dao {

    private final JdbcTemplate jdbcTemplate;

    public GameDBdao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game start(Game game) {

        final String sql = "INSERT INTO Game(answer, inProgress, wrongGuess) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.isInProgress());
            statement.setInt(3, game.getWrongGuess());
            return statement;

        }, keyHolder);

        game.setId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public Round guess(Game game, String guess) {
        final String sql = "INSERT INTO round(guess, currentAnswer, gameId) VALUES(?, ?, ? );";
        jdbcTemplate.update(sql, guess, game.getId() );

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



    @Override
    public Game findById(int gameId) {
        final String sql = "Select * from Game where GameID = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), gameId);
    }


    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setAnswer((rs.getString("answer")));
            game.setInProgress(rs.getBoolean("inProgress"));
            game.setWrongGuess(rs.getInt("wrongGuess"));

            return game;
        }

    }
}
