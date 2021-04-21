package com.example.pierrefeuilleciseaux;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameSQL extends SQLiteOpenHelper {

    private final static String DB_NAME = "game.db";
    private final static int DB_VERSION = 3;


    public GameSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE Game(" +
                "idPlayer INTEGER PRIMARY KEY AUTOINCREMENT," +
                "namePlayer TEXT," +
                "gamePlayer INTEGER," +
                "scorePlayer INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Game");
    }

    /**
     * get player
     * @param playerName
     * @return
     */
    public Player getPlayer(String playerName) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM Game", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("namePlayer"));
                int score = cursor.getInt(cursor.getColumnIndex("scorePlayer"));
                int numberGame = cursor.getInt(cursor.getColumnIndex("gamePlayer"));
                if (name.equals(playerName)) {
                    return new Player(name,score,numberGame);
                }
            } while (cursor.moveToNext());
        }
        return new Player(playerName);
    }


    /**
     * Insert or update player
     * @param player
     */
    public void insertOrUpdatePlayer(Player player) {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM Game", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("namePlayer"));
                if (name.equals(player.getName())) {
                    update(player);
                    return;
                }
            } while (cursor.moveToNext());
        }

        insert(player);
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
     * Insert player on database
     * @param player
     */
    private void insert(Player player) {
        String REQUEST = "INSERT INTO Game (namePlayer,scorePlayer,gamePlayer) VALUES(?,?,?)";
        Object[] args = {player.getName(), player.getScore(),player.getNumberGame()};
        this.getWritableDatabase().execSQL(REQUEST, args);
    }


    /**
     * update player on database
     * @param player
     */
    private void update(Player player) {
        String REQUEST = "UPDATE Game SET scorePlayer=? , gamePlayer=? WHERE namePlayer=?";
        Object[] args = {player.getScore(), player.getNumberGame(),player.getName()};
        this.getWritableDatabase().execSQL(REQUEST, args);
    }
}
