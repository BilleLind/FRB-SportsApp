package com.example.eksamensprojekt.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import com.example.eksamensprojekt.data.model.Bruger;

import com.example.eksamensprojekt.presentation.viewmodel.BeskedViewModel;
import com.example.eksamensprojekt.repository.BeskedRepository;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.example.eksamensprojekt.utils.Konstante.brugere;
import static com.example.eksamensprojekt.utils.Konstante.chats;

public class BeskedActivity extends AppCompatActivity {

    ShapeableImageView profilBillede;

    ImageButton send_btn;
    EditText besked_send;
    FirebaseUser firebaseBruger;
    DatabaseReference databaseReference;
    BeskedAdapter beskedAdapter;
    List<Besked> beskedList;
    RecyclerView recyclerView;
    Intent intent;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler

    BeskedViewModel beskedViewModel;

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
        actionBarMenu = (ImageView) findViewById(R.id.action_bar_logo);

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
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BeskedActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        intent = getIntent();
        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser(); // en "fail safe" for at undgå at BrugerID ikke er kommet tilbage før den skal bruges
        final String bruger_id;
        String data = getIntent().getStringExtra("brugerid");
        if (data == null) {
            bruger_id = firebaseBruger.getUid();
        } else {
            bruger_id = getIntent().getStringExtra("brugerid");
        }

        recyclerView = findViewById(R.id.recycler_view); // her forbindes recyclerView med det recyclerView i activity_besked
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true); // gør så recyclerView bliver fyldt ud begyndende fra bunden.
        recyclerView.setLayoutManager(linearLayoutManager);


        send_btn.setOnClickListener(new View.OnClickListener() { // ved at bruge en onClickListener gør det muligt at først at sende eller gemme besked til når afsender er klar
            @Override
            public void onClick(View v) {
                String msg = besked_send.getText().toString();
                if (!msg.equals("")) { //nogle lille kontrol for at forhindre spamming med tomme beskeder.
                    sendBesked(firebaseBruger.getUid(), bruger_id, msg); // måske det kun er msg der skal gøres igennem, resten i reposotioriet
                } else {
                    Toast.makeText(BeskedActivity.this, "ikke muligt at sende tomme beskeder", Toast.LENGTH_SHORT).show();
                }
                besked_send.setText(""); // sørger for at brugeren ikke skal slette sine egne beskeder efter man har sendt beskeden.
            }
        });


        //TODO skal alt sammen sikkert ind i repositoriet og observer ind hertil
        databaseReference = FirebaseDatabase.getInstance().getReference(brugere).child(bruger_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bruger bruger = dataSnapshot.getValue(Bruger.class);

                laesBesked(firebaseBruger.getUid(), bruger_id, bruger.getBilledeURL()); //billede ikke indført
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void sendBesked(String afsender, String modtager, String besked) {
       Besked beskedny = new Besked();
       beskedny.setAfsender(afsender);
       beskedny.setModtager(modtager);
       beskedny.setBesked(besked);
       beskedViewModel.nyBesked(beskedny); // sender videre til BeskedViewModel ved brug af nyBesked(beskedny) metoden.
    }

    private void laesBesked(final String minid, final String brugerId, final String billedeURL) { // denne metode henter beskederne i databasen, hvor den kontrollere om de individuelle beskeder har relevans for brugeren
        beskedList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference(chats);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // DataSnapshot modtages hver gang man aflæser Database data, man modtager data'en som et DataSnapshot
                beskedList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //"dataSnapshot.getChildren()" giver adgang til de nærmeste dataSnapshot
                    Besked besked = snapshot.getValue(Besked.class); //her marshaller man data'en fra snapshot ind igennem Besked klassen, så afsender er forbundet med en String kaldet afsender så Besked.getAfsender
                    if (besked.getModtager().equals(minid) && besked.getAfsender().equals(brugerId) ||
                            besked.getModtager().equals(brugerId) && besked.getAfsender().equals(minid)) { // her kontrollere man om det nedhentede har relevans for sin bruger
                        beskedList.add(besked); // hvis den har så tilføjes den til et Arraylist
                    }
                    beskedAdapter = new BeskedAdapter(BeskedActivity.this, beskedList, billedeURL); // ArrayList bliver sammen sat med BeskedAdapter i BeskedActivity
                    recyclerView.setAdapter(beskedAdapter); //hvor den bliver sat sammen med et RecyclerView som viser beskederne ved at bruge metoderne og Layout's som er forbundet med BeskedAdapteren

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
