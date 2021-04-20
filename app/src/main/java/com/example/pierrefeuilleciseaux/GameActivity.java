package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_stone;
    private ImageView iv_leaf;
    private ImageView iv_scissors;
    private TextView tv_score;
    private TextView tv_name;
    private TextView tv_p1ScoreNow;
    private TextView tv_p2ScoreNow;
    private TextView tv_round;
    private int score;
    private int round;
    private Game game;
    private GameSQL gameSQL;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        Intent intent = getIntent();
        name=intent.getStringExtra("playerName");
        tv_name.setText(name);
        score = intent.getIntExtra("playerScore",0);
        game = new Game();
        round=0;
        gameSQL=new GameSQL(this);
        updateTvScore();
    }


    /**
     * Init view
     */
    public void init(){
        iv_stone = findViewById(R.id.imageView_Stone);
        iv_leaf=findViewById(R.id.imageView_Leaf);
        iv_scissors=findViewById(R.id.imageView_Scissors);
        tv_p1ScoreNow = findViewById(R.id.textView_ScoreP1Now);
        tv_p2ScoreNow=findViewById(R.id.textView_ScoreP2Now);
        tv_name = findViewById(R.id.textView_NameValue);
        tv_round = findViewById(R.id.textView_RoundValue);
        tv_score=findViewById(R.id.textView_ScoreValue);
        iv_stone.setOnClickListener(this);
        iv_leaf.setOnClickListener(this);
        iv_scissors.setOnClickListener(this);
    }

    /**
     * by clicking on one of the buttons, I play the ea and I determine who wins
     * @param v
     */
    @Override
    public void onClick(View v) {
        int choiceIA = Game.playIA();
        int choicePlayer=-1;
        switch (v.getId()){
            case R.id.imageView_Leaf:
                game.play(Game.LEAF,choiceIA);
                choicePlayer=Game.LEAF;
                break;
            case R.id.imageView_Scissors:
                game.play(Game.SCISSORS,choiceIA);
                choicePlayer=Game.SCISSORS;
                break;
            case R.id.imageView_Stone :
                game.play(Game.ROC,choiceIA);
                choicePlayer=Game.ROC;
                break;
        }

        Toast.makeText(this,"Joueur 1 : "+Game.getString(choicePlayer)+"  Ordinateur : "+Game.getString(choiceIA),Toast.LENGTH_SHORT).show();
        tv_p1ScoreNow.setText(String.valueOf(game.getPlayer1Score()));
        tv_p2ScoreNow.setText(String.valueOf(game.getPlayer2Score()));
        round++;
        if(game.isFinish()){
            if(game.getPlayer1Score()>game.getPlayer2Score()){
                score++;
                updateTvScore();
                gameSQL.insertOrUpdatePlayer(name,score);
            }
            round=0;
            game.newGame();
        }
        tv_round.setText(String.valueOf(round));
    }

    /**
     * Update score view
     */
    private void updateTvScore(){
        tv_score.setText(String.valueOf(score));

    }

}