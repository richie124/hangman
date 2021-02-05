package com.coolkids.hangman.data;

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

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, round.getGuess());
            statement.setString(2, round.getCurrentAnswer());
            statement.setInt(3, round.getGameId());
            return statement;

        }, keyHolder);

        round.setId(keyHolder.getKey().intValue());

        return round;
    }


    @Override
    public Game findGameById(int gameId) {
        final String sql = "Select * from Game where id = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), gameId);
    }

    @Override
    public Round findRoundById(int roundId) {
        final String sql = "Select * from round where id = ? ;";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), roundId);
    }



    @Override
    public Game updateGame(Game game) {
        final String sql = "UPDATE game SET "
                + "inProgress = ?, "
                + "wrongGuess = ? "
                + "WHERE id = ?;";

        jdbcTemplate.update(sql, game.isInProgress(), game.getWrongGuess(), game.getId());
        return findGameById(game.getId());
    }

    @Override
    public Round findPrevRoundByGameId(int gameId) {
        final String sql = "Select * from round where gameId = ? order by id desc limit 1;";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), gameId);
    }

    @Override
    public Integer getRoundCountByGameId(int gameId) {
        final String sql = "select count(*) from round where gameId=?;";

        return jdbcTemplate.queryForObject(sql, Integer.class, gameId);
    }

    @Override
    public List<Round> findRoundByGameId(int id) {
        final String sql = "SELECT * FROM round WHERE gameId = ? ORDER BY id DESC;";
        return jdbcTemplate.query(sql, new RoundMapper(), id);
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
