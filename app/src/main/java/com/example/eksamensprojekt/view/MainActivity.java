package com.example.eksamensprojekt.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.eksamensprojekt.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Imageview fra actionbar
    ImageView actionBarProfil;
    ImageView actionBarChat;

    //Button for main xml
    Button seTraerning;
    Button bookTid;



    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // check if user is null
        if (firebaseUser != null ) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // mAuth = FirebaseAuth.getInstance();

        /* Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout); */

        //Adding custom action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);


        //Sætter ids til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);

        seTraerning = (Button) findViewById(R.id.goto_feedback_btn);
        bookTid = (Button) findViewById(R.id.goto_booking_btn);

        //skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VisProfilActivity.class));
                finish();

            }
        });

        //skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                finish();
            }
        });


        //skifter til book tid activity
        bookTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, BookTidActivity.class));
                finish();
            }
        });

        //skifter til feedback activity
        seTraerning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                finish();
            }
        });
    }
}




