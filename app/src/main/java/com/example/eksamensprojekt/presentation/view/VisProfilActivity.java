package com.example.eksamensprojekt.presentation.view;



import android.content.Intent;
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


    private Button seTraeningBtn, seBeskederBtn, logUdBtn, bookTidBtn;

    private TextView visFuldenavn, visEmail, visTelefonNr, visNaesteBooking;

    private Button brugerLogUdKnap, chatButton, træningButton;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseBruger;
    ImageView actionBarProfil, actionBarChat, actionBarHome; //Action Bar Variabler

    private FirebaseAuth mAuth;
    private FirebaseUser fireBruger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis_profil);

        //Tilføjer custom action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        mAuth = FirebaseAuth.getInstance();
        fireBruger = FirebaseAuth.getInstance().getCurrentUser();

        
        bookTidBtn = (Button) findViewById(R.id.goto_booking_btn);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarHome = (ImageView) findViewById(R.id.action_bar_home);


        //Sætter de forskellige views til at ændre layoutet
        logUdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getInstance().signOut();
                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();

            }
        });

        seTraeningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, FeedbackActivity.class));
                finish();
            }
        });



        //Skifter til menu activity
        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, ChatActivity.class));
                finish();

            }
        });

        bookTidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, BookTidActivity.class));
                finish();
            }
        });


        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        brugerLogUdKnap = (Button) findViewById(R.id.log_ud_btn);
        chatButton = (Button) findViewById(R.id.goto_chat_btn);
        træningButton = (Button) findViewById(R.id.goto_traening_btn);


                startActivity(new Intent(VisProfilActivity.this, ChatActivity.class));
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



   /* private void udfyldProfil() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(brugere).child(fireBruger.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bruger bruger = dataSnapshot.getValue(Bruger.class);
                String fuldeNavn = bruger.getFornavn() +" "+ bruger.getEfternavn();
                visFuldenavn.setText(fuldeNavn);
                String email = bruger.getEmail();
                visEmail.setText(email);
                String tele = bruger.getTelefonNr();
                visTelefonNr.setText(tele);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/



    //Tjekker om bruger er logget ind. Hvis ikke, skifter til opret bruger activity
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);


        if (currentUser == null) {

            Intent opretBrugerIntent = new Intent(VisProfilActivity.this, OpretBrugerActivity.class);
            startActivity(opretBrugerIntent);
            finish();
        }

    }
}
