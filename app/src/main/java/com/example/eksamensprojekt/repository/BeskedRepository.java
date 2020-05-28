package com.example.eksamensprojekt.repository;


import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.eksamensprojekt.data.model.Besked;
import com.example.eksamensprojekt.presentation.adapter.BeskedAdapter;
import com.example.eksamensprojekt.presentation.view.BeskedActivity;
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

import static com.example.eksamensprojekt.utils.Konstante.brugerId;
import static com.example.eksamensprojekt.utils.Konstante.brugere;
import static com.example.eksamensprojekt.utils.Konstante.chats;

public class BeskedRepository {

    private FirebaseAuth validering = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference(brugere);
    private FirebaseUser firebaseBruger = validering.getCurrentUser();
    Intent intent;

    public MutableLiveData<Besked> sendNyBesked(Besked nybesked) { // had it in List, didn't work in ViewModel
        final MutableLiveData<Besked> nyBeskedMutableLiveData = new MutableLiveData<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("afsender", nybesked.getAfsender());
        hashMap.put("modtager", nybesked.getModtager());
        hashMap.put("besked", nybesked.getBesked());

        databaseReference.child("Chats").push().setValue(hashMap);

        return nyBeskedMutableLiveData;
    }

    public MutableLiveData<Besked> modtagBesked() {
        final MutableLiveData<Besked> modtagBeskeder = new MutableLiveData<>();
        //TODO flyt til en anden klasse og implementer

            final List<Besked> beskedList = new ArrayList<>();

            databaseReference = FirebaseDatabase.getInstance().getReference(chats);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    beskedList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Besked besked = snapshot.getValue(Besked.class);
                        if (besked.getModtager().equals(firebaseBruger) && besked.getAfsender().equals(intent.getStringExtra("brugerid")) ||
                                besked.getModtager().equals(intent.getStringExtra("brugerid")) && besked.getAfsender().equals(firebaseBruger)) {
                            beskedList.add(besked);
                        }
                        //beskedAdapter = new BeskedAdapter(BeskedActivity.this, beskedList, billedeURL);
                        //recyclerView.setAdapter(beskedAdapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        return modtagBeskeder;
    }

}
