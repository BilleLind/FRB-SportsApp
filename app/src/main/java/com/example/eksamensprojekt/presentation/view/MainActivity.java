package com.example.eksamensprojekt.presentation.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.eksamensprojekt.R;

import com.example.eksamensprojekt.presentation.common.MyTimer;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    ImageView actionBarProfil, actionBarChat; //Action Bar Variabler
    ImageView massage, fysioterapi, akupunktur;

    //Button til main XML
    Button seTraerningKnap;
    Button bookTidKnap;

    FirebaseUser firebaseUser;

    long startTime = MyTimer.currentTimestamp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // firebaseAuth = FirebaseAuth.getInstance();

        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Forbinder IDs til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);

        Date date=new Date(startTime);
        System.out.println("Time is " + date.toString() + " (" + date.getTime() + ")\n");
        System.out.println("Calculation starting.");

        //Skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProfil(v);

                long endTime = MyTimer.currentTimestamp();
                String resultat = MyTimer.timeElapsed(startTime, endTime) ;
                System.out.println();
                Log.i("EXECUTETIME", resultat);
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        seTraerningKnap = (Button) findViewById(R.id.begynd_Traening_Button);
        bookTidKnap = (Button) findViewById(R.id.goto_booking_btn);
        massage = (ImageView) findViewById(R.id.massage);
        fysioterapi = (ImageView) findViewById(R.id.fysio);
        akupunktur = (ImageView) findViewById(R.id.aku);

        //Skifter til trænings oversigt activity
        seTraerningKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IntroTilOovelserActivity.class));
                finish();
            }
        });

        //Skifter til book tid activity fra book knap
        bookTidKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookTidActivity.class));
                finish();
            }
        });

        //Skifter til book tid activity fra Massage knap
        massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookTidActivity.class));
                finish();
            }
        });

        //Skifter til book tid activity fra Fysio knap
        fysioterapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookTidActivity.class));
                finish();
            }
        });

        //Skifter til book tid activity fra akupunktur knap
        akupunktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookTidActivity.class));
                finish();
            }
        });

    }

    private void gotoProfil(View view) {
        Intent intent = new Intent (this, VisProfilActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}




