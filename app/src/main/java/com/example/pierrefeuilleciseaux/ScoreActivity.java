package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

public class ScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameSQL gameSQL;
    private RecyclerView.LayoutManager layoutManager;
    private ScoreAdapter scoreAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        init();
        gameSQL=new GameSQL(this);
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        scoreAdapter = new ScoreAdapter(this,gameSQL.getScoreList());
        recyclerView.setAdapter(scoreAdapter);
    }

    public void init(){
        recyclerView = findViewById(R.id.rv_list);
    }

}