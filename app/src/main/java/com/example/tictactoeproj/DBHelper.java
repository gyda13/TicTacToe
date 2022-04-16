package com.example.tictactoeproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class DBHelper extends SQLiteOpenHelper {



    SQLiteDatabase db;
    public static final String DBNAME="playerdata.db";
    public DBHelper(Context context) {


        super(context, "playerdata.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table player(playerOne TEXT , playerTwo TEXT,winner TEXT )");









    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists player");



    }

    public Boolean InsertData(String playerOne,String playerTwo,String winner){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("playerOne",playerOne);
        values.put("playerTwo",playerTwo);
        values.put("winner",winner);

        long result=db.insert("player",null,values);
        if(result==-1) return false;
        else
            return true;

    }
    public Boolean delete() {

        long result=db.delete("player", null, null);
        if(result==-1) return true;
        else
            return false;
    }






}
