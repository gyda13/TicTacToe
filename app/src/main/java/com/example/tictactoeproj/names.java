package com.example.tictactoeproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class names extends AppCompatActivity {

    Button play;
    EditText playerone,playerTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        playerone=findViewById(R.id.playerone);
        playerTwo=findViewById(R.id.playerTwo);
        play =findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String one=playerone.getText().toString();
                String two=playerTwo.getText().toString();

                Intent intent = new Intent(getApplicationContext(), playervsplayer.class);
                intent.putExtra("one",one);
               intent.putExtra("two",two);
                startActivity(intent);
            }
        });

    }
}