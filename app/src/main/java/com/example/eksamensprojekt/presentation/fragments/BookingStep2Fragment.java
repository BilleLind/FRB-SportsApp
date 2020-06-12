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
import com.example.eksamensprojekt.presentation.Interface.AlleAnsatteLoadListener;
import com.example.eksamensprojekt.presentation.adapter.AnsatteAdapter;
import com.example.eksamensprojekt.presentation.common.SpacesItemDecoration;
import com.example.eksamensprojekt.presentation.presenter.AnsattePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingStep2Fragment extends Fragment implements AlleAnsatteLoadListener {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    CollectionReference alleAnsatteRef;

    AlleAnsatteLoadListener alleAnsatteLoadListener;

    static BookingStep2Fragment instance;

    public static BookingStep2Fragment getInstance(){
        if (instance == null)
            instance = new BookingStep2Fragment();

        return instance;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String branchRef = "Ansatte";

        alleAnsatteRef = FirebaseFirestore.getInstance().collection("AlleAnsatte")
                .document(branchRef).collection("Branch");
        alleAnsatteLoadListener = this;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_2, container, false);

        loadAlleAnsatte();

        return itemView;
    }

    private void loadAlleAnsatte() {

        alleAnsatteRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<AnsattePresenter> list = new ArrayList<>();
                    if (task.isSuccessful()){

                        for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                            list.add(documentSnapshot.toObject(AnsattePresenter.class));
                        alleAnsatteLoadListener.onAllAnsatteLoadSucces(list);
                    }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alleAnsatteLoadListener.onAllAnsatteLoadFail(e.getMessage());
            }
        });
    }

    @Override
    public void onAllAnsatteLoadSucces(List<AnsattePresenter> ansatteList) {

        RecyclerView ansatteRecycler = (RecyclerView)this.getView().findViewById(R.id.recycler_ansatte);
        AnsatteAdapter adapter = new AnsatteAdapter(getActivity(),ansatteList);

        ansatteRecycler.setAdapter(adapter);
        ansatteRecycler.setHasFixedSize(true);
        ansatteRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ansatteRecycler.addItemDecoration(new SpacesItemDecoration(4));
        ansatteRecycler.setVisibility(View.VISIBLE);


    }

    @Override
    public void onAllAnsatteLoadFail(String message) {


    }
}
