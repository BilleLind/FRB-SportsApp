package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler
    TextView spoorgsmaal_Text_View;
    RadioButton super_Traening_Button, okay_Traening_Button, kunne_Forbedres_Button, daarlig_Oplevelse_Button;
    Button gennemfoort_Traening_Button;
    Boolean feedbackChosen = false;
    TextInputLayout feedback;

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

        super_Traening_Button = (RadioButton) findViewById(R.id.super_Traening_Button);
        okay_Traening_Button = (RadioButton) findViewById(R.id.okay_Traening_Button);
        kunne_Forbedres_Button = (RadioButton) findViewById(R.id.kunne_Forbedres_Button);
        daarlig_Oplevelse_Button = (RadioButton) findViewById(R.id.daarlig_Oplevelse_Button);
        gennemfoort_Traening_Button = (Button) findViewById(R.id.gennemfoort_Traening_Button);
        feedback = (TextInputLayout) findViewById(R.id.feedback_Input);

        //Checker om bruger har valgt feedback
        super_Traening_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackChosen = true;
                feedback.setVisibility(0); //Gør feedback input kassen synlig
            }
        });
        okay_Traening_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackChosen = true;
                feedback.setVisibility(0);
            }
        });
        kunne_Forbedres_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackChosen = true;
                feedback.setVisibility(0);
            }
        });
        daarlig_Oplevelse_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackChosen = true;
                feedback.setVisibility(0);
            }
        });
        // ^^Feedback radio knap check ^^

        //Skifter til profil activity
        gennemfoort_Traening_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (super_Traening_Button.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Det vi glade for at høre :)", Toast.LENGTH_SHORT).show();
                }
                if (feedbackChosen == true) {
                    startActivity(new Intent(FeedbackActivity.this, VisProfilActivity.class));
                    finish();
                }
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
