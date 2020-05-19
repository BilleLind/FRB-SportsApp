package com.example.eksamensprojekt.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.adapter.BeskedAdapter;
import com.example.eksamensprojekt.model.Bruger;

import com.example.eksamensprojekt.model.Chat;
import com.google.android.material.appbar.MaterialToolbar;
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

public class BeskedActivity extends AppCompatActivity {


    ShapeableImageView profile_billede;
    TextView brugerNavn;

    ImageButton send_btn;
    EditText besked_send;

    FirebaseUser fireBruger;
    DatabaseReference reference;

    BeskedAdapter beskedAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besked);
        intent = getIntent();
        fireBruger = FirebaseAuth.getInstance().getCurrentUser();
        final String user_id;
        String data = getIntent().getStringExtra("userid");
        if (data == null) {
            user_id = fireBruger.getUid();
        } else {
            user_id = getIntent().getStringExtra("userid");
        }

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.recycler_view_besked);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        brugerNavn = findViewById(R.id.brugerNavnChat); // have to be the on shown in main.xml or it will crash when clicking on
        send_btn = findViewById(R.id.btn_send);
        besked_send = findViewById(R.id.besked_send);
        profile_billede = findViewById(R.id.profile_billede);




        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = besked_send.getText().toString();
                if (!msg.equals("")) {
                    sendBesked(fireBruger.getUid(), user_id, msg);
                } else {
                    Toast.makeText(BeskedActivity.this, "ikke muligt at sende tomme beskeder", Toast.LENGTH_SHORT).show();
                }
                besked_send.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Users").child(user_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bruger bruger  =dataSnapshot.getValue(Bruger.class);
                brugerNavn.setText(bruger.getBrugerNavn());

                if (bruger.getBilledeURL().equals("default")) {
                    profile_billede.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(BeskedActivity.this).load(bruger.getBilledeURL()).into(profile_billede);
                }
                laesBesked(fireBruger.getUid(), user_id, bruger.getBilledeURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void sendBesked(String afsender, String modtager, String besked) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap =  new HashMap<>();
        hashMap.put("afsender", afsender);
        hashMap.put("modtager", modtager);
        hashMap.put("besked", besked);

        reference.child("Chats").push().setValue(hashMap);

    }

    private void laesBesked(final String minid, final String userid, final String billedeURL) {
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getModtager().equals(minid) && chat.getAfsender().equals(userid) ||
                    chat.getModtager().equals(userid) && chat.getAfsender().equals(minid)) {
                        mChat.add(chat);
                    }

                    beskedAdapter = new BeskedAdapter(BeskedActivity.this,mChat,billedeURL);
                        recyclerView.setAdapter(beskedAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}