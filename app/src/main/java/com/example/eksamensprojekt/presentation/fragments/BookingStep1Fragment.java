package com.example.eksamensprojekt.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.presentation.Interface.AlleBehandlingerLoadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class BookingStep1Fragment extends Fragment implements AlleBehandlingerLoadListener {

    //Variabler



    CollectionReference alleBehandlingerRef;
    CollectionReference branchRef;

    private BookingStep1Fragment binding;

    AlleBehandlingerLoadListener alleBehandlingerLoadListener;


    static BookingStep1Fragment instance;

    public static BookingStep1Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep1Fragment();

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        alleBehandlingerRef = FirebaseFirestore.getInstance().collection("AlleBehandlinger");
        alleBehandlingerLoadListener = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_1, container, false);
        
        loadAlleBehandlinger();

        return itemView;


    }

    private void loadAlleBehandlinger() {

        alleBehandlingerRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<String> list = new ArrayList<>();
                    list.add("Vælg en behandling");
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                        list.add(documentSnapshot.getId());
                    alleBehandlingerLoadListener.onAllBehandlingerLoadSucces(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                alleBehandlingerLoadListener.onAllBehandlingerLoadFail(e.getMessage());

            }
        });


    }

    @Override
    public void onAllBehandlingerLoadSucces(List<String> behandlingsTyperList) {

        MaterialSpinner spinnerStep1 = (MaterialSpinner) getView().findViewById(R.id.spinner_step1);

        spinnerStep1.setItems(behandlingsTyperList);

    }

    @Override
    public void onAllBehandlingerLoadFail(String message) {
        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();

    }
}