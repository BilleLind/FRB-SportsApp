package com.example.eksamensprojekt.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.presentation.Interface.AlleTiderLoadListener;
import com.example.eksamensprojekt.presentation.adapter.TiderAdapter;
import com.example.eksamensprojekt.presentation.common.SpacesItemDecoration;
import com.example.eksamensprojekt.presentation.presenter.TiderPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingStep3Fragment extends Fragment implements AlleTiderLoadListener {

    CollectionReference alleTiderRef;

    AlleTiderLoadListener alleTiderLoadListener;

    static BookingStep3Fragment instance;

    public static BookingStep3Fragment getInstance(){
        if (instance == null)
            instance = new BookingStep3Fragment();

        return instance;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String branchRef = "Tider";

        alleTiderRef = FirebaseFirestore.getInstance().collection("AlleTider")
                .document(branchRef).collection("Branch");
        alleTiderLoadListener = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView = inflater.inflate(R.layout.fragment_booking_step_3, container, false);

        loadAlleTider();

        return itemView;
    }

    private void loadAlleTider() {

        alleTiderRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<TiderPresenter> list = new ArrayList<>();
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                        list.add(documentSnapshot.toObject(TiderPresenter.class));
                    alleTiderLoadListener.onAllTiderLoadSucces(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alleTiderLoadListener.onAllTiderLoadFail(e.getMessage());
            }
        });
    }

    @Override
    public void onAllTiderLoadSucces(List<TiderPresenter> tiderList) {

        RecyclerView tiderRecycler = (RecyclerView)this.getView().findViewById(R.id.recycler_tider);
        TiderAdapter adapter = new TiderAdapter(getActivity(),tiderList);

        tiderRecycler.setAdapter(adapter);
        tiderRecycler.setHasFixedSize(true);
        tiderRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        tiderRecycler.addItemDecoration(new SpacesItemDecoration(4));
        tiderRecycler.setVisibility(View.VISIBLE);


    }

    @Override
    public void onAllTiderLoadFail(String message) {

    }

}
