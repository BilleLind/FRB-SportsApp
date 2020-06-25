package com.example.eksamensprojekt.presentation.view;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;

import com.example.eksamensprojekt.data.model.Bruger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.eksamensprojekt.presentation.Interface.Konstante.brugere;

public class VisProfilActivity extends AppCompatActivity {


    private Button brugerLogUdKnap, chatButton, træningButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseBruger;
    ImageView actionBarProfil, actionBarChat, actionBarHome; //Action Bar Variabler

    private TextView visFuldeNavn, visEmail, visTelefonNr;

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
        actionBarHome = (ImageView) findViewById(R.id.action_bar_home);

        //til Top layout
        visFuldeNavn = findViewById(R.id.vis_fuldenavn_tv);
        visEmail = findViewById(R.id.vis_email_tv);
        visTelefonNr = findViewById(R.id.vis_telefonNr_tv);

        udfyldProfil();


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
        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        firebaseAuth = FirebaseAuth.getInstance();

        brugerLogUdKnap = (Button) findViewById(R.id.log_ud_btn);
        chatButton = (Button) findViewById(R.id.goto_chat_btn);
        træningButton = (Button) findViewById(R.id.goto_traening_btn);


        brugerLogUdKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisProfilActivity.this, ChatActivity.class));
                finish();
            }
        });

        træningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisProfilActivity.this, IntroTilOovelserActivity.class));
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

     private void udfyldProfil() {
        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(brugere).child(firebaseBruger.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bruger bruger = dataSnapshot.getValue(Bruger.class);
                String fuldeNavn = bruger.getFornavn() +" "+ bruger.getEfternavn();
                visFuldeNavn.setText(fuldeNavn);
                String email = bruger.getEmail();
                visEmail.setText(email);
                String tele = bruger.getTelefonNr();
                visTelefonNr.setText("Tlf: " + tele);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
