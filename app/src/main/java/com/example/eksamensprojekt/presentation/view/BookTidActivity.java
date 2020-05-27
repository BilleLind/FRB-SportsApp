package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.eksamensprojekt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookTidActivity extends AppCompatActivity {

    private StepView stepView;
    private ViewPager viewPager;

    private Button naesteStep, tilbageStep;

    private ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tid);
        //viewbind??


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
                startActivity(new Intent(BookTidActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BookTidActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BookTidActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^


        firebaseAuth = FirebaseAuth.getInstance();

        stepView = (StepView) findViewById(R.id.step_view);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        naesteStep = (Button) findViewById(R.id.naeste_step_btn);
        tilbageStep = (Button) findViewById(R.id.tilbage_step_btn);

        setupStepView();
        setColorButton();

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    private void setColorButton() {
        if (naesteStep.isEnabled()){
            naesteStep.setBackgroundResource(R.color.colorAccent);

        }else {
            naesteStep.setBackgroundResource(R.color.colorPrimary);
        }

        if (tilbageStep.isEnabled()){
            tilbageStep.setBackgroundResource(R.color.colorAccent);

        }else {
            tilbageStep.setBackgroundResource(R.color.colorPrimary);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Behandling");
        stepList.add("Behandler");
        stepList.add("Tidspunkt");
        stepList.add("Bekræft");
        stepView.setSteps(stepList);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);


        if (currentUser == null) {

            Intent signInIntent = new Intent(BookTidActivity.this, OpretBrugerActivity.class);
            startActivity(signInIntent);
            finish();
        }


    }

}
