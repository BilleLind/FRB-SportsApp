package com.example.eksamensprojekt.presentation.fragments;

import android.app.ProgressDialog;
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
import com.example.eksamensprojekt.presentation.Interface.AlleBehandlingerLoadListener;
import com.example.eksamensprojekt.presentation.Interface.BranchLoadListener;
import com.example.eksamensprojekt.presentation.adapter.BehandlingerAdapter;
import com.example.eksamensprojekt.presentation.common.SpacesItemDecoration;
import com.example.eksamensprojekt.presentation.presenter.BehandlingPresenter;
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

public class BookingStep1Fragment extends Fragment implements AlleBehandlingerLoadListener, BranchLoadListener {

    //Variabler





    CollectionReference alleBehandlingerRef;
    CollectionReference branchRef;

    AlleBehandlingerLoadListener alleBehandlingerLoadListener;
    BranchLoadListener branchLoadListener;

    ProgressDialog ventProgress;


    static BookingStep1Fragment instance;

    public static BookingStep1Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep1Fragment();

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        alleBehandlingerRef = FirebaseFirestore.getInstance().collection("AlleBehandlinger");
        alleBehandlingerLoadListener = this;
        branchLoadListener = this;





        //ventProgress = new ProgressDialog(getActivity());


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_1, container, false);

        loadAlleBehandlinger();

        return itemView;


    }






    private void loadAlleBehandlinger() {

        alleBehandlingerRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    list.add("Vælg en behandling");
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                        list.add(documentSnapshot.getId());
                    alleBehandlingerLoadListener.onAllBehandlingerLoadSucces(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alleBehandlingerLoadListener.onAllBehandlingerLoadFail(e.getMessage());

            }
        });


    }

    @Override
    public void onAllBehandlingerLoadSucces(List<String> behandlingsTyperList) {

        MaterialSpinner spinnerStep1 = (MaterialSpinner) getView().findViewById(R.id.spinner_step1);
        spinnerStep1.setItems(behandlingsTyperList);
        spinnerStep1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (position > 0){
                    loadBranchOfBehandling(item.toString());
                }else{
                    //behandlingsrecycler.setvisibility(View.GONE);
                }
            }
        });



    }


    private void loadBranchOfBehandling(String behandlingsTidOgPris) {
        //ventProgress.show();

        branchRef = FirebaseFirestore.getInstance()
                .collection("AlleBehandlinger")
                .document(behandlingsTidOgPris)
                .collection("Branch");

        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<BehandlingPresenter> list = new ArrayList<>();
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                        list.add(documentSnapshot.toObject(BehandlingPresenter.class));
                    branchLoadListener.onBranchLoadSucces(list);


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                branchLoadListener.onBranchLoadFail(e.getMessage());

            }
        });
    }


    @Override
    public void onAllBehandlingerLoadFail(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onBranchLoadSucces(List<BehandlingPresenter> behandlingsList) {


        RecyclerView behandlingerRecycler = (RecyclerView)this.getView().findViewById(R.id.recycler_behandlinger);
        BehandlingerAdapter adapter = new BehandlingerAdapter(getActivity(),behandlingsList);

        behandlingerRecycler.setAdapter(adapter);
        behandlingerRecycler.setHasFixedSize(true);
        behandlingerRecycler.setLayoutManager(new GridLayoutManager(getActivity(),3));
        behandlingerRecycler.addItemDecoration(new SpacesItemDecoration(4));
        behandlingerRecycler.setVisibility(View.VISIBLE);


        //ventProgress.dismiss();


    }

    @Override
    public void onBranchLoadFail(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        //ventProgress.dismiss();



    }


}
