package com.example.eksamensprojekt.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Bruger;
import com.example.eksamensprojekt.data.model.SamtaleListe;
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

import static com.example.eksamensprojekt.presentation.Interface.Konstante.brugere;


public class ChatsFragment extends Fragment {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private RecyclerView recyclerView;

    private BrugerAdapter brugerAdapter;
    private List<Bruger> brugerListe;

    FirebaseUser firebaseBruger;
    DatabaseReference databaseReference;

    private List<SamtaleListe> samtalelisten;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser();

        samtalelisten = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("samtaleListe").child(firebaseBruger.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                samtalelisten.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SamtaleListe samtaleListe = snapshot.getValue(SamtaleListe.class);
                    samtalelisten.add(samtaleListe);
                }

                samtaleTilBruger();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void samtaleTilBruger() {
        brugerListe = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference(brugere);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                brugerListe.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bruger bruger = snapshot.getValue(Bruger.class);
                    for (SamtaleListe samtaleListe : samtalelisten) {
                        if (bruger.getId().equals(samtaleListe.getBrugerid())) {
                            brugerListe.add(bruger);
                        }
                    }
                }
                brugerAdapter = new BrugerAdapter(getContext(), brugerListe);
                recyclerView.setAdapter(brugerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
