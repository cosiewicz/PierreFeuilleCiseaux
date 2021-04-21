package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button bt_play;
    private Button bt_score;
    private GameSQL gameSQL;


    /**
     * on click ok button on alert box i open new activity with player data
     */
    AlertBox.Listener alertBoxListener = new AlertBox.Listener() {
        @Override
        public void onValidate(String value) {
            if(value.length()>0){
                Player player = gameSQL.getPlayer(value);
                Intent intent = new Intent(getBaseContext(),GameActivity.class);
                intent.putExtra("player",player);
                startActivity(intent);
            }
            else{
                Toast.makeText(getBaseContext(),"Nom du joueur obligatoire",Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     * on pressed button i show alert box and get player name
     */
    View.OnClickListener bt_playListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertBox alertBox = new AlertBox(MainActivity.this,"Entrez votre nom");
            alertBox.setListener(alertBoxListener);
        }
    };

    /**
     * On click i open score list activity
     */
    View.OnClickListener bt_scoreListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(),ScoreActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        gameSQL = new GameSQL(this);
    }


    /**
     * Init view
     */
    public void init(){
        bt_play = findViewById(R.id.button_Play);
        bt_score = findViewById(R.id.button_Score);

        bt_play.setOnClickListener(bt_playListener);
        bt_score.setOnClickListener(bt_scoreListener);
    }


}