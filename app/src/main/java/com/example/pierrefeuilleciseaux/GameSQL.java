package com.example.pierrefeuilleciseaux;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameSQL extends SQLiteOpenHelper {

    private final static String DB_NAME = "game.db";
    private final static int DB_VERSION = 1;


    public GameSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE Game(" +
                "idPlayer INTEGER PRIMARY KEY AUTOINCREMENT," +
                "namePlayer TEXT," +
                "scorePlayer INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Game");
    }

    /**
     * get player score
     * @param playerName
     * @return
     */
    public int getScore(String playerName) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM Game", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("namePlayer"));
                int score = cursor.getInt(cursor.getColumnIndex("scorePlayer"));
                if (name.equals(playerName)) {
                    return score;
                }
            } while (cursor.moveToNext());
        }
        return 0;
    }

    /**
     * Update or insert player score if exist or not
     * @param playerName
     * @param playerScore
     */
    public void insertOrUpdatePlayer(String playerName, int playerScore) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM Game", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("namePlayer"));
                if (name.equals(playerName)) {
                    update(playerName, playerScore);
                    return;
                }
            } while (cursor.moveToNext());
        }

        insert(playerName, playerScore);
    }

    /**
     * get scoreList
     * @return
     */
    public Cursor getScoreList(){
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM Game", null);
        cursor.moveToFirst();
        return cursor;
    }


    /**
     * Insert value
     * @param player
     * @param score
     */
    private void insert(String player, int score) {
        String REQUEST = "INSERT INTO Game (namePlayer,scorePlayer) VALUES(?,?)";
        Object[] args = {player, score};
        this.getWritableDatabase().execSQL(REQUEST, args);
    }

    /**
     * Update value
     * @param player
     * @param score
     */
    private void update(String player, int score) {
        String REQUEST = "UPDATE Game SET scorePlayer=? WHERE namePlayer=?";
        Object[] args = {score, player};
        this.getWritableDatabase().execSQL(REQUEST, args);
    }
}
