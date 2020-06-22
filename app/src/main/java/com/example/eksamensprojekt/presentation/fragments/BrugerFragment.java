package com.example.eksamensprojekt.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.presentation.adapter.BrugerAdapter;
import com.example.eksamensprojekt.data.model.Bruger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.eksamensprojekt.presentation.Interface.Konstante.brugere;


public class    BrugerFragment extends Fragment {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */
    private RecyclerView recyclerView;

    private BrugerAdapter brugerAdapter;
    private List<Bruger> brugerList;

    EditText soeg_brugere;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bruger, container,false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        brugerList = new ArrayList<>();

        visBrugere();


        soeg_brugere = view.findViewById(R.id.soege_bruger);
        soeg_brugere.setInputType(8192); // 8192 som inputType gør så at det første character bliver stort. det er for at gøre det lettere at søge, med den måde som fornavn bliver gemt på.
        soeg_brugere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                soegEfterBrugere(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void soegEfterBrugere(String s) {
        final FirebaseUser firebaseBruger = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference(brugere).orderByChild("fornavn").startAt(s).endAt(s +"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                brugerList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bruger bruger = snapshot.getValue(Bruger.class);

                    if (!bruger.getId().equals(firebaseBruger.getUid())) {
                        brugerList.add(bruger);
                    }
                }
                brugerAdapter = new BrugerAdapter(getContext(), brugerList);
                recyclerView.setAdapter(brugerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void visBrugere() {

        final FirebaseUser firebaseBruger = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(brugere);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (soeg_brugere.getText().toString().trim().equals("")) {
                    brugerList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Bruger bruger = snapshot.getValue(Bruger.class);

                        assert firebaseBruger != null;
                        assert bruger != null;
                        if (!bruger.getId().equals(firebaseBruger.getUid())) { // asserts if there are any other user than the current logged in and adds them to the user fragment activity
                            brugerList.add(bruger);
                        }

                    }
                    brugerAdapter = new BrugerAdapter(getContext(), brugerList);
                    recyclerView.setAdapter(brugerAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
