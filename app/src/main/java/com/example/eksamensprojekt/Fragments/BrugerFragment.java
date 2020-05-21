package com.example.eksamensprojekt.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.adapter.BrugerAdapter;
import com.example.eksamensprojekt.model.Bruger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BrugerFragment extends Fragment {
    private RecyclerView recyclerView;

    private com.example.eksamensprojekt.adapter.BrugerAdapter brugerAdapter;
    private List<Bruger> mBrugere;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bruger, container,false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBrugere = new ArrayList<>();

        readUsers();

        return view;
    }


    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Brugere");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBrugere.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bruger bruger =  snapshot.getValue(Bruger.class);

                    assert firebaseUser != null;
                    assert bruger != null;
                    //TODO find why i haven't initialized bruger for the getBrugerid
                    if (!bruger.getId().equals(firebaseUser.getUid())) { // asserts if there are any other user than the current logged in and adds them to the user fragment activity
                        mBrugere.add(bruger);
                    }
                }
                brugerAdapter = new BrugerAdapter(getContext(), mBrugere);
                recyclerView.setAdapter(brugerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
