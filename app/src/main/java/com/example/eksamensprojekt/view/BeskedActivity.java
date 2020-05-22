package com.example.eksamensprojekt.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.adapter.BeskedAdapter;
import com.example.eksamensprojekt.model.Bruger;

import com.example.eksamensprojekt.model.Chat;
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

public class BeskedActivity extends AppCompatActivity {

    ShapeableImageView profilBillede;
    TextView fornavn;
    ImageButton send_btn;
    EditText besked_send;
    FirebaseUser firebaseBruger;
    DatabaseReference databaseReference;
    BeskedAdapter beskedAdapter;
    List<Chat> chatList;
    RecyclerView recyclerView;
    Intent intent;
    ImageView actionBarProfil,actionBarChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besked);
        //Tilføjer custom action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        intent = getIntent();
        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser(); // en "fail safe" for at undgå at brugerid ikke er kommet tilbage før den skal bruges
        final String bruger_id;
        String data = getIntent().getStringExtra("brugerid");
        if (data == null) {
            bruger_id = firebaseBruger.getUid();
        } else {
            bruger_id = getIntent().getStringExtra("brugerid");
        }
        //Sætter ids til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);

        //skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeskedActivity.this, VisProfilActivity.class));
                finish();

            }
        });

        //skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BeskedActivity.this, ChatActivity.class));
                finish();
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        fornavn = findViewById(R.id.fornavn); // nødvendigt at have som koden er nuværende
        send_btn = findViewById(R.id.btn_send);
        besked_send = findViewById(R.id.besked_send);
        profilBillede = findViewById(R.id.profile_billede);




        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = besked_send.getText().toString();
                if (!msg.equals("")) {
                    sendBesked(firebaseBruger.getUid(), bruger_id, msg);
                } else {
                    Toast.makeText(BeskedActivity.this, "ikke muligt at sende tomme beskeder", Toast.LENGTH_SHORT).show();
                }
                besked_send.setText("");
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("Brugere").child(bruger_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bruger bruger  =dataSnapshot.getValue(Bruger.class);
                fornavn.setText(bruger.getFornavn());


                /*TODO if (bruger.getBilledeURL().equals("default")) {
                    profile_billede.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(BeskedActivity.this).load(bruger.getBilledeURL()).into(profile_billede);
                } */
                laesBesked(firebaseBruger.getUid(), bruger_id, bruger.getBilledeURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void sendBesked(String afsender, String modtager, String besked) { // sender besked ved at lægge det ind i en hashmap, med afsender og modtagers brugerid samt beskeden
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap =  new HashMap<>();
        hashMap.put("afsender", afsender);
        hashMap.put("modtager", modtager);
        hashMap.put("besked", besked);

        reference.child("Chats").push().setValue(hashMap);

    }

    private void laesBesked(final String minid, final String brugerId, final String billedeURL) { //
        chatList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getModtager().equals(minid) && chat.getAfsender().equals(brugerId) ||
                    chat.getModtager().equals(brugerId) && chat.getAfsender().equals(minid)) {
                        chatList.add(chat);
                    }

                    beskedAdapter = new BeskedAdapter(BeskedActivity.this, chatList, billedeURL);
                        recyclerView.setAdapter(beskedAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
