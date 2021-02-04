package com.coolkids.hangman.models;

public class Game {

    private int id;
    private String answer;
    private boolean inProgress;
    private int wrongGuess;

    public Game() {
        inProgress = true;
        wrongGuess = 0;
    }

    private String generateAnswer() {
        return "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public int getWrongGuess() {
        return wrongGuess;
    }

    public void setWrongGuess(int wrongGuess) {
        this.wrongGuess = wrongGuess;
    }
}
