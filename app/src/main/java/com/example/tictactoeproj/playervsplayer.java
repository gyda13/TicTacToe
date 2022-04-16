package com.example.tictactoeproj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class playervsplayer extends AppCompatActivity implements View.OnClickListener {


    private TextView playerOneScore, playerTwoScore, playerStatus,playerOne,playerTwo;
    private Button[] buttons = new Button[9];
    private Button restGame,home,pre;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer; //first player

    DBHelper DB;

    SQLiteDatabase database;





    // player one = 0
    // player two = 1
    // empty = 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //columns
            {2, 4, 6}, {0, 4, 8}//cross
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playervsplayer);

        DB=new DBHelper(this);
        playerOne = findViewById(R.id.playerOne);
        playerTwo= findViewById(R.id.playerTwo);
        Bundle extras=getIntent().getExtras();
        String playerOnename="";
        String playerTwoname="";
        if(extras !=null){
            playerOnename=extras.getString("one");
            playerTwoname=extras.getString("two");
        }
        playerOne.setText("X/ "+playerOnename);
        playerTwo.setText("O/ "+playerTwoname);

        playerOne=findViewById(R.id.playerOne);
        playerTwo=findViewById(R.id.playerTwo);
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        restGame = (Button) findViewById(R.id.restGame);
        home = (Button) findViewById(R.id.home);
        pre = (Button) findViewById(R.id.pre);



        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);

        }
        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog=new AlertDialog.Builder(playervsplayer.this).create();
                alertDialog.setTitle("Alert");

                alertDialog.setMessage("Are you sure you want to exit the game?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"YES", new DialogInterface.OnClickListener(){
                   public void onClick(DialogInterface dialog,int which){
                       database=DB.getWritableDatabase();
                       String one=playerOne.getText().toString();
                       String two=playerTwo.getText().toString();
                       String winner=playerStatus.getText().toString();


                       database.execSQL("INSERT INTO player(playerOne,playerTwo,winner) VALUES ('"+one+"','"+two+"','"+winner+"')");

                       Intent intent = new Intent(getApplicationContext(), resulte.class);
                       playervsplayer.this.startActivity(intent);
                   }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){

                    }
                });


                alertDialog.show();


            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), names.class);
                playervsplayer.this.startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {


        Bundle extras=getIntent().getExtras();
       String playerOnename=extras.getString("one");
       String playerTwoname=extras.getString("two");

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));

        if (activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#FF3700B3"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, playerOnename+"  Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, playerTwoname+"   Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (rountCount == 9) {
            playAgain();
            Toast.makeText(this, "No Winner :(", Toast.LENGTH_SHORT).show();

        } else {
            activePlayer = !activePlayer;
        }
        if (playerOneScoreCount > playerTwoScoreCount) {
            playerStatus.setText(playerOnename+"  is winning :)");
        } else if (playerTwoScoreCount > playerOneScoreCount) {
            playerStatus.setText(playerTwoname+"  is winning :)");

        } else {
            playerStatus.setText("");
        }
        restGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });

    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int[] winPosion : winningPositions) {
            if (gameState[winPosion[0]] == gameState[winPosion[1]] &&
                    gameState[winPosion[1]] == gameState[winPosion[2]] &&
                    gameState[winPosion[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }


    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain() {
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}