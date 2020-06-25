package com.example.eksamensprojekt.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Besked;
import com.example.eksamensprojekt.presentation.adapter.BeskedAdapter;



import com.example.eksamensprojekt.presentation.viewmodel.BeskedViewModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


import static com.example.eksamensprojekt.presentation.Interface.Konstante.chats;
/*
Licensed to the Apache Software Foundation (ASF) under one
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
under the License.
 */
public class BeskedActivity extends AppCompatActivity {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */


    private ShapeableImageView profilBillede;

    private ImageButton send_btn;
    private EditText besked_send;
    private FirebaseUser firebaseBruger;
    private DatabaseReference databaseReference;
    private BeskedAdapter beskedAdapter;
    private List<Besked> beskedList;
    private RecyclerView recyclerView;
    private ImageView actionBarProfil, actionBarChat, actionBarHome; //Action Bar Variabler

    private BeskedViewModel beskedViewModel;


    ValueEventListener setBeskedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besked);
        send_btn = findViewById(R.id.btn_send);
        besked_send = findViewById(R.id.besked_send);
        profilBillede = findViewById(R.id.profile_billede);

        //nødvendigt at initialisere ViewModel, ellers kom der nullPointException
        beskedViewModel = new ViewModelProvider(this).get(BeskedViewModel.class);

        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Forbinder IDs til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarHome = (ImageView) findViewById(R.id.action_bar_home);

        //Skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeskedActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BeskedActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BeskedActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^


        skafModtagerId();
        
        recyclerView = findViewById(R.id.recycler_view); // her forbindes recyclerView med det recyclerView i activity_besked
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true); // gør så recyclerView bliver fyldt ud begyndende fra bunden.
        recyclerView.setLayoutManager(linearLayoutManager);


        send_btn.setOnClickListener(new View.OnClickListener() { // ved at bruge en onClickListener gør det muligt at først at sende eller gemme besked til når afsender er klar
            @Override
            public void onClick(View v) {
                sendBeskedCheck();
            }
        });

                modtagBesked(firebaseBruger.getUid(), skafModtagerId()); //billede ikke indført
    }

    private String skafModtagerId() {
        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser(); // en "fail safe" for at undgå at BrugerID ikke er kommet tilbage før den skal bruges
        final String modtagerId = getIntent().getStringExtra("brugerid");
        return modtagerId;
}

    private void sendBeskedCheck() {
        String msg = besked_send.getText().toString();
        if (!msg.equals("")) {
            sendBesked(firebaseBruger.getUid(), skafModtagerId(), msg);
        }else {
            Toast.makeText(BeskedActivity.this, "ikke muligt at sende tomme beskeder", Toast.LENGTH_SHORT).show();
        }
        besked_send.setText(""); // sørger for at brugeren ikke skal slette sine egne beskeder efter man har sendt beskeden.
    }



   /* private void setBesked(final String brugerId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        setBeskedListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Besked besked = snapshot.getValue(Besked.class);
                    if (besked.getModtager().equals(firebaseBruger.getUid()) && besked.getAfsender().equals(brugerId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("erSet", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    } */

    private void sendBesked(String afsender, String modtager, String besked) {
       Besked beskedny = new Besked();
       beskedny.setAfsender(afsender);
       beskedny.setModtager(modtager);
       beskedny.setBesked(besked);
       Long tidSomLong = System.currentTimeMillis()/1000;
       beskedny.setTid(tidSomLong);
       beskedViewModel.nyBesked(beskedny); // sender videre til BeskedViewModel ved brug af nyBesked(beskedny) metoden.
    }

    private void modtagBesked(final String minid, final String brugerId) { // denne metode henter beskederne i databasen, hvor den kontrollere om de individuelle beskeder har relevans for brugeren
        beskedList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference(chats);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // DataSnapshot modtages hver gang man aflæser Database data, man modtager data'en som et DataSnapshot
                beskedList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //"dataSnapshot.getChildren()" giver adgang til de nærmeste dataSnapshot
                    Besked besked = snapshot.getValue(Besked.class); //her marshaller man data'en fra snapshot ind igennem Besked klassen, så afsender er forbundet med en String kaldet afsender så Besked.getAfsender
                    assert besked != null;
                    if (besked.getModtager().equals(minid) && besked.getAfsender().equals(brugerId) ||
                            besked.getModtager().equals(brugerId) && besked.getAfsender().equals(minid)) { // her kontrollere man om det nedhentede har relevans for sin bruger
                        beskedList.add(besked); // hvis den har så tilføjes den til et Arraylist
                    }
                    beskedAdapter = new BeskedAdapter(BeskedActivity.this, beskedList); // ArrayList bliver sammen sat med BeskedAdapter i BeskedActivity
                    recyclerView.setAdapter(beskedAdapter); //hvor den bliver sat sammen med et RecyclerView som viser beskederne ved at bruge metoderne og Layout's som er forbundet med BeskedAdapteren

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
