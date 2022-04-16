package com.example.tictactoeproj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class resulte extends AppCompatActivity {

    ListView lv;
    DBHelper controllerDB;
    SQLiteDatabase db;

    Button done;

    private ArrayList<String> playerone=new ArrayList<>();
    private ArrayList<String> playertwo=new ArrayList<>();
    private ArrayList<String> winner=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte);

        lv=findViewById(R.id.lstvw);
        controllerDB=new DBHelper(this);

        done = (Button) findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), finish.class);
                resulte.this.startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        DisplayData();
        super.onResume();
    }

    @SuppressLint("Range")
    public void DisplayData(){
        db=controllerDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM player",null);
        playerone.clear();
        playertwo.clear();
        winner.clear();

        if (cursor.moveToFirst()){
            do{
                System.out.println("Inside Loop..");
                playerone.add(cursor.getString(cursor.getColumnIndex("playerOne")));
                playertwo.add(cursor.getString(cursor.getColumnIndex("playerTwo")));
                winner.add(cursor.getString(cursor.getColumnIndex("winner")));

            }while (cursor.moveToNext());

        }

        CustomAdapter ca=new CustomAdapter(resulte.this,playerone,playertwo,winner);
        lv.setAdapter(ca);
        cursor.close();
    }
}