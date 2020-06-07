package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Oovelser;
import com.example.eksamensprojekt.presentation.adapter.OovelserOversigtAdapter;
import com.example.eksamensprojekt.presentation.viewmodel.IntroTilOovelserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntroTilOovelserActivity extends AppCompatActivity {

    private static final String TAG = "IntroTilOovelserActivit"; //Debugging

    //Variabler
    private FirebaseAuth firebaseAuth;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler
    Button klar_Button;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private IntroTilOovelserViewModel mIntroTilOovelserViewModel; //Nyt ViewModel objekt
    private RecyclerView mRecyclerView;
    private OovelserOversigtAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__til__oovelser);

        Log.d(TAG, "onCreate: started."); //Debugging

        mRecyclerView = findViewById(R.id.oversigt_recycler_view);

        mIntroTilOovelserViewModel = new ViewModelProvider(this).get(IntroTilOovelserViewModel.class); //Instansierer min ViewModel

        mIntroTilOovelserViewModel.init(); //Henter data fra Repository

        mIntroTilOovelserViewModel.getOovelser().observe(this, new Observer<List<Oovelser>>() { //Observerer ændringer i ViewModel af LiveData objekterne.
            @Override
            public void onChanged(List<Oovelser> oovelsers) { //Hver gang noget data ændrer sig, så vil det følgende blive eksekveret
                mAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();

        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Forbinder IDs til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarMenu = (ImageView) findViewById(R.id.action_bar_logo);

        //Skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroTilOovelserActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(IntroTilOovelserActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(IntroTilOovelserActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        klar_Button = (Button) findViewById(R.id.klar_Button);

//Skifter til start træning activity
        klar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroTilOovelserActivity.this, StartTraeningActivity.class));
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        mAdapter = new OovelserOversigtAdapter(this, mIntroTilOovelserViewModel.getOovelser().getValue());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);

        if (currentUser == null) {

            Intent signInIntent = new Intent(IntroTilOovelserActivity.this, OpretBrugerActivity.class);
            startActivity(signInIntent);
            finish();
        }
    }
}