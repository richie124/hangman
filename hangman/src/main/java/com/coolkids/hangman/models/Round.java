package com.coolkids.hangman.models;

public class Round {
    private int id;
    private char guess;
    private String currentAnswer;
    private int gameId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getGuess() {
        return guess;
    }

    public void setGuess(char guess) {
        this.guess = guess;
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentAnswer(String currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
