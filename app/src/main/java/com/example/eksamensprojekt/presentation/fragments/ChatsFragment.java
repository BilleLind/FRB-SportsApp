package com.example.eksamensprojekt.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Besked;
import com.example.eksamensprojekt.data.model.Bruger;
import com.example.eksamensprojekt.presentation.adapter.BrugerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private RecyclerView recyclerView;

    private BrugerAdapter brugerAdapter;
    private List<Bruger> brugere;

    FirebaseUser firebaseBruger;
    DatabaseReference databaseReference;

    private List<String> brugerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser();

        brugerList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                brugerList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Besked besked = snapshot.getValue(Besked.class);

                    if (besked.getAfsender().equals(firebaseBruger.getUid())) { //TODO når min struktur for chats ændre skal disse også ændres
                        brugerList.add(besked.getModtager());
                    }
                    if (besked.getModtager().equals(firebaseBruger.getUid())) {
                        brugerList.add(besked.getAfsender());
                    }
                }
                visAktiveSamtaler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void visAktiveSamtaler() {
        brugere = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Brugere");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                brugere.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bruger bruger = snapshot.getValue(Bruger.class);

                    for (String id : brugerList) {
                        if (bruger.getId().equals(id)) {
                            if (brugere.size() != 0) {
                                for (Bruger bruger1 : brugere) {
                                    if (!bruger.getId().equals(bruger1.getId())) {
                                        brugere.add(bruger);
                                    }
                                }
                            } else {
                                brugere.add(bruger);
                            }
                        }
                    }
                }
                brugerAdapter = new BrugerAdapter(getContext(), brugere);
                recyclerView.setAdapter(brugerAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
