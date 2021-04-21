package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_stone;
    private ImageView iv_leaf;
    private ImageView iv_scissors;
    private ImageView iv_player;
    private ImageView iv_IA;
    private TextView tv_result;
    private TextView tv_score;
    private TextView tv_name;
    private TextView tv_p1ScoreNow;
    private TextView tv_p2ScoreNow;
    private TextView tv_round;
    private Button bt_newGame;
    private Button bt_stopGame;
    private Button bt_quit;
    private int round;
    private Game game;
    private GameSQL gameSQL;
    private Player player;

    View.OnClickListener bt_quitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener bt_newGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            newGame();
        }
    };

    View.OnClickListener bt_stopGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            player.looseGame();
            gameIsFinish();
            gameSQL.insertOrUpdatePlayer(player);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        Intent intent = getIntent();
        player=(Player)intent.getSerializableExtra("player");
        tv_name.setText(player.getName());
        game = new Game();
        round=0;
        gameSQL=new GameSQL(this);
        updateTvScore();
        bt_newGame.setEnabled(false);
    }


    /**
     * Init view
     */
    public void init(){
        iv_stone = findViewById(R.id.imageView_Stone);
        iv_leaf=findViewById(R.id.imageView_Leaf);
        iv_scissors=findViewById(R.id.imageView_Scissors);
        iv_player = findViewById(R.id.imageView_player);
        iv_IA=findViewById(R.id.imageView_player2);
        bt_quit=findViewById(R.id.button_quit);
        tv_result = findViewById(R.id.textView_result);
        tv_p1ScoreNow = findViewById(R.id.textView_ScoreP1Now);
        tv_p2ScoreNow=findViewById(R.id.textView_ScoreP2Now);
        tv_name = findViewById(R.id.textView_NameValue);
        tv_round = findViewById(R.id.textView_RoundValue);
        tv_score=findViewById(R.id.textView_ScoreValue);
        bt_newGame = findViewById(R.id.button_newGame);
        bt_quit.setOnClickListener(bt_quitListener);
        bt_stopGame = findViewById(R.id.buttonStop);
        bt_stopGame.setOnClickListener(bt_stopGameListener);
        bt_newGame.setOnClickListener(bt_newGameListener);
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
        int result=0;
        switch (v.getId()){
            case R.id.imageView_Leaf:
                result=game.play(Game.LEAF,choiceIA);
                choicePlayer=Game.LEAF;
                break;
            case R.id.imageView_Scissors:
                result=game.play(Game.SCISSORS,choiceIA);
                choicePlayer=Game.SCISSORS;
                break;
            case R.id.imageView_Stone :
                result=game.play(Game.ROC,choiceIA);
                choicePlayer=Game.ROC;
                break;
        }
        showRoundResult(choicePlayer,choiceIA,result);
        Toast.makeText(this,"Joueur 1 : "+Game.getString(choicePlayer)+"  Ordinateur : "+Game.getString(choiceIA),Toast.LENGTH_SHORT).show();
        tv_p1ScoreNow.setText(String.valueOf(game.getPlayer1Score()));
        tv_p2ScoreNow.setText(String.valueOf(game.getPlayer2Score()));
        round++;
        if(game.isFinish()){
            if(game.getPlayer1Score()>game.getPlayer2Score()){
                player.winGame();
            }
            else{
                player.looseGame();
            }
            gameSQL.insertOrUpdatePlayer(player);
            gameIsFinish();
        }
        tv_round.setText(String.valueOf(round));
    }

    /**
     * Update score view
     */
    private void updateTvScore(){
        tv_score.setText(String.valueOf(player.getScore()));
    }

    /**
     * Lock imageView if finish
     */
    private void gameIsFinish(){
        bt_newGame.setEnabled(true);
        bt_stopGame.setEnabled(false);
        iv_leaf.setEnabled(false);
        iv_scissors.setEnabled(false);
        iv_stone.setEnabled(false);
        updateTvScore();

    }

    /**
     * New game
     */
    private void newGame(){
        round=0;
        game.newGame();
        bt_newGame.setEnabled(false);
        bt_stopGame.setEnabled(true);
        iv_leaf.setEnabled(true);
        iv_scissors.setEnabled(true);
        iv_stone.setEnabled(true);
        updateTvScore();
        tv_round.setText(String.valueOf(round));
        tv_p1ScoreNow.setText(String.valueOf(game.getPlayer1Score()));
        tv_p2ScoreNow.setText(String.valueOf(game.getPlayer2Score()));
    }

    private void showRoundResult(int playerChoice, int IAChoice, int result){
        switch (playerChoice){
            case Game.LEAF:
                iv_player.setImageDrawable(iv_leaf.getDrawable());
                break;
            case Game.SCISSORS:
                iv_player.setImageDrawable(iv_scissors.getDrawable());
                break;
            case Game.ROC :
                iv_player.setImageDrawable(iv_stone.getDrawable());
                break;
        }

        switch (IAChoice){
            case Game.LEAF:
                iv_IA.setImageDrawable(iv_leaf.getDrawable());
                break;
            case Game.SCISSORS:
                iv_IA.setImageDrawable(iv_scissors.getDrawable());
                break;
            case Game.ROC :
                iv_IA.setImageDrawable(iv_stone.getDrawable());
                break;
        }

        if(result==1){
            tv_result.setText("Joueur 1 gagne!");
        }
        else if(result==-1){
            tv_result.setText("Joueur 2 gagne!");
        }
        else{
            tv_result.setText("Egalité");
        }
    }
}