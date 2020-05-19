package com.example.eksamensprojekt.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.eksamensprojekt.R;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Imageview fra actionbar
    ImageView actionBarProfil;
    ImageView actionBarChat;

    //Button for main xml
    Button seTraerning;
    Button bookTid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
