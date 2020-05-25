package com.example.eksamensprojekt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eksamensprojekt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class VisProfilActivity extends AppCompatActivity {

    private Button brugerLogUdKnap;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseBruger;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis_profil);


        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Forbinder IDs til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarMenu = (ImageView) findViewById(R.id.action_bar_logo);

        //Skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisProfilActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        firebaseAuth = FirebaseAuth.getInstance();

        brugerLogUdKnap = (Button) findViewById(R.id.log_ud_btn);


        brugerLogUdKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    //Tjekker om bruger er logget ind. Hvis ikke, bliver bruger præsenteret for opret bruger aktiviteten.
    @Override
    public void onStart() {
        super.onStart();

        // Tjek om bruger er logged in (ikke null) og opdater UI som nødvendigt.
        firebaseBruger = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);

        if (firebaseBruger == null) {

            Intent ikkeLoggetIndIntent = new Intent(VisProfilActivity.this, OpretBrugerActivity.class);
            startActivity(ikkeLoggetIndIntent);
            finish();
        }
    }
}
