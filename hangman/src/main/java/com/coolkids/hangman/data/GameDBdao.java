package com.coolkids.hangman.data;

import com.coolkids.hangman.data.Dao;
import com.coolkids.hangman.models.Game;
import com.coolkids.hangman.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class GameDBdao implements Dao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
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
    public Round guess(Round round) {
        final String sql = "INSERT INTO round(guess, currentAnswer, gameId) VALUES(?, ?, ? );";
        jdbcTemplate.update(sql, round.getGuess(), round.getCurrentAnswer(), round.getGameId() );

        final String roundSql = "Select * from Round where id = ? ;";
        return jdbcTemplate.queryForObject(roundSql, new RoundMapper(), round.getId());
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

    @Override
    public Round findRoundById(int roundId) {
        final String sql = "Select * from round where id = ?;";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), roundId);
    }

    //GameMapper:
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

    //add RoundMapper:
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setGuess((rs.getString("guess")));
            round.setCurrentAnswer((rs.getString("currentAnswer")));
            round.setGameId(rs.getInt("gameId"));

            return round;
        }
    }
}
