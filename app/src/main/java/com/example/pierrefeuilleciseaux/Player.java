package com.example.pierrefeuilleciseaux;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int score;
    private int numberGame;


    /**
     * Constructor
     * @param name
     * @param score
     * @param numberGame
     */
    public Player(String name, int score, int numberGame) {
        this.name = name;
        this.score = score;
        this.numberGame = numberGame;
    }

    /**
     * Constructor
     * @param name
     */
    public Player(String name) {
        this.name=name;
        score=0;
        numberGame=0;
    }

    /**
     * Inc score and game if win
     */
    public void winGame(){
        score++;
        numberGame++;
    }

    /**
     * dec score and game if loose
     */
    public void looseGame(){
        score--;
        numberGame++;
    }


    // Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberGame() {
        return numberGame;
    }

    public void setNumberGame(int numberGame) {
        this.numberGame = numberGame;
    }
}
