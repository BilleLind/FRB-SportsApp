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
import com.example.eksamensprojekt.presentation.adapter.BookingViewPagerAdapter;
import com.example.eksamensprojekt.presentation.common.common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kofigyan.stateprogressbar.StateProgressBar;


import java.util.Objects;
/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.  */


public class BookTidActivity extends AppCompatActivity {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */


    //Variabler
    private ViewPager viewPager;

    private Button naesteStep, tilbageStep;

    String[] descriptionData = {"Behandling", "Behandler", "Tidspunkt", "Bekræft"};

    private ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tid);

        //Tilføjer custom progress bar
        final StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.booking_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //instantiere variabler med view ids
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


        //instantiere variabler med view ids
        firebaseAuth = FirebaseAuth.getInstance();

        viewPager = (ViewPager) findViewById(R.id.view_pager_booking);

        naesteStep = (Button) findViewById(R.id.naeste_step_btn);
        tilbageStep = (Button) findViewById(R.id.tilbage_step_btn);

        //ToDo on click listener til at skifte fragment ved button click


        naesteStep.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                common.step++;

                if (common.step == 1) {
                    viewPager.setCurrentItem(1, true);

                }else if (common.step == 2){
                    viewPager.setCurrentItem(2, true);

                }else if (common.step == 3){
                    viewPager.setCurrentItem(3, true);

                }

            }
        });

        tilbageStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                common.step--;

                if (common.step == 0){
                    viewPager.setCurrentItem(0, true);

                }else if (common.step == 1) {
                    viewPager.setCurrentItem(1, true);

                }else if (common.step == 2){
                    viewPager.setCurrentItem(2, true);

                }else if (common.step == 3){
                    viewPager.setCurrentItem(3, true);

                }
            }
        });



        viewPager.setAdapter(new BookingViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //Ændre progress baren efter hvilken fragment bruger befinder sig på
            @Override
            public void onPageSelected(int position) {


                if (position == 0){
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

                }
                else if (position == 1){
                   stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

                }
                else if (position == 2){
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

                }
                else if (position == 3){
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
