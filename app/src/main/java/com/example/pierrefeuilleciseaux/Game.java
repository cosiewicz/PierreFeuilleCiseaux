package com.example.pierrefeuilleciseaux;

import android.util.Log;

import java.util.Random;

public class Game {

    public final static int ROC = 0;
    public final static int LEAF = 1;
    public final static int SCISSORS = 2;

    private int player1Score;
    private int player2Score;


    private int[][] gameResult = {
            {0, -1, 1},
            {1, 0, -1},
            {-1, 1, 0}
    };

    /**
     * Constructor
     */
    public Game() {
        player1Score=0;
        player2Score=0;
    }

    /*
    Simulate a player and choose a hand
     */
    public static int playIA(){
        final int min = 0;
        final int max = 2;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    /**
     * Determine the winner of the round and set score
     * @param p1Choice
     * @param p2Choice
     */
    public void play(int p1Choice,int p2Choice){

        if(gameResult[p1Choice][p2Choice]==1){
            player1Score++;
            player2Score--;

        }
        else if(gameResult[p1Choice][p2Choice]==-1) {
            player1Score--;
            player2Score++;

        }
    }

    /**
     * Start new game
     */
    public void newGame(){
        player2Score=0;
        player1Score=0;
    }


    /**
     * Convert int value to string
     * @param value
     * @return
     */
    public static String getString(int value){
        String val="";
        switch (value){
            case ROC :
                val= "Pierre";
                break;
            case LEAF:
                val= "Feuille";
                break;
            case SCISSORS:
                val= "Ciseaux";
                break;
        }
        return val;
    }


    /**
     * Getter score value player 1
     * @return
     */
    public int getPlayer1Score() {
        return player1Score;
    }

    /**
     * Getter score value player 2
     * @return
     */
    public int getPlayer2Score() {
        return player2Score;
    }

    /**
     * if a player has more than 4 point return true and he wins
     * @return
     */
    public boolean isFinish(){
        if (player1Score>=5 || player2Score>=5){
            return true;
        }
        else{
            return false;
        }
    }
}
