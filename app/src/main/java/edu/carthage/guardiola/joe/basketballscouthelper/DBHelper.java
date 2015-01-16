package edu.carthage.guardiola.joe.basketballscouthelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Joe on 1/13/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, "BasketballScoutHelper.db", null, 2);
    }

    public synchronized static DBHelper getInstance(Context context) {
        //if the one and only object has NOT been created yet
        if(instance == null) {
            //call the private constructor and create it
            instance = new DBHelper(context);
        }

        //always return a reference to the one and only DBHelper
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create my database tables for players and game stats
        db.execSQL("CREATE TABLE Player (playerId INTEGER PRIMARY KEY NOT NULL, first TEXT, last TEXT, number INTEGER, team TEXT, height TEXT, yearsExp INTEGER, position TEXT)");
        db.execSQL("CREATE TABLE GameStats (gameId INTEGER PRIMARY KEY NOT NULL, playerId INTEGER, opponent TEXT, fga INTEGER, fgm INTEGER, points REAL, steals REAL" +
                "assists REAL, offensiveRebounds REAL, defensiveRebounds REAL, blocks REAL, threefga INTEGER, threefgm INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertAPlayer(String first, String last, int number, String team, String height, int yearsExp, String position) {
        //use the db helper to get a writable db
        SQLiteDatabase db = getWritableDatabase();

        //create the values to insert for a store
        ContentValues cv = new ContentValues();
        cv.put("first", first);
        cv.put("last", last);
        cv.put("number", number);
        cv.put("team", team);
        cv.put("height", height);
        cv.put("yearsExp", yearsExp);
        cv.put("position", position);

        //insert into the db
        db.insert("Player", null, cv);
    }

    public void insertGameStats(int playerId, String opponent, int fga, int fgm, double points, double steals, double assists, double offensiveRebounds, double defensiveRebounds, double blocks,  int threefga, int threefgm) {
        //use the db helper to get a writable db
        SQLiteDatabase db = getWritableDatabase();

        //create the values to insert for a store
        ContentValues cv = new ContentValues();
        cv.put("playerId", playerId);
        cv.put("opponent", opponent);
        cv.put("fga", fga);
        cv.put("fgm", fgm);
        cv.put("points", points);
        cv.put("steals", steals);
        cv.put("assists", assists);
        cv.put("offensiveRebounds", offensiveRebounds);
        cv.put("defensiveRebounds", defensiveRebounds);
        cv.put("blocks", blocks);
        cv.put("threefga", threefga);
        cv.put("threefgm", threefgm);

        //insert into the db
        db.insert("GameStats", null, cv);
    }

    public Cursor getAllPlayerNames() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT playerId AS _id, first || ' ' || last AS playerName ");
        builder.append("FROM Player ");
        builder.append("ORDER BY playerName;");

        Log.d("DBHelper", builder.toString());

        //use the db helper to get a database object for reading only
        SQLiteDatabase db = getReadableDatabase();

        //query to get the id and store name/location (cursor adapter needs a unique field called _id so we rename the id)
        Cursor allPlayerNames = db.rawQuery(builder.toString(), null);

        return allPlayerNames;
    }

    public Cursor averageAStat(int playerId, String statName){
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT AVG(GameStat." + statName + ") As averageOfStat ");
        builder.append("FROM Player, GameStat ");
        builder.append("WHERE Player.playerId = " + playerId + " ");
        builder.append("AND GameStat.playerId = " +  playerId + " ");

        Log.d("DBHelper", builder.toString());
        SQLiteDatabase db = getReadableDatabase();
        Cursor averageOfStat = db.rawQuery(builder.toString(), null);

        return averageOfStat;
    }

    public Cursor getPlayerAttributes(int playerId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT number, team, height, yearsExp, position ");
        builder.append("FROM Player ");
        builder.append("WHERE playerId = " + playerId);

        Log.d("DBHelper", builder.toString());
        SQLiteDatabase db = getReadableDatabase();
        Cursor playerInformation = db.rawQuery(builder.toString(), null);

        return playerInformation;
    }
}
