package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler
    TextView spoorgsmaal_Text_View;
    RadioButton super_Traening_Button, okay_Traening_Button, kunne_Forbedres_Button, daarlig_Oplevelse_Button;
    Button gennemfoort_Traening_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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
                startActivity(new Intent(FeedbackActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FeedbackActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FeedbackActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        gennemfoort_Traening_Button = (Button) findViewById(R.id.gennemfoort_Traening_Button);
        super_Traening_Button = (RadioButton) findViewById(R.id.super_Traening_Button);
        okay_Traening_Button = (RadioButton) findViewById(R.id.okay_Traening_Button);
        kunne_Forbedres_Button = (RadioButton) findViewById(R.id.kunne_Forbedres_Button);
        daarlig_Oplevelse_Button = (RadioButton) findViewById(R.id.daarlig_Oplevelse_Button);

        //Skifter til profil activity
        gennemfoort_Traening_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (super_Traening_Button.isEnabled()) {
                    Toast.makeText(getApplicationContext(),"Det vi glade for at høre :)",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(FeedbackActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);

        if (currentUser == null) {

            Intent signInIntent = new Intent(FeedbackActivity.this, OpretBrugerActivity.class);
            startActivity(signInIntent);
            finish();
        }
    }
}
