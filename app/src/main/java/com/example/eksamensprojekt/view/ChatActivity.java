package com.example.eksamensprojekt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.eksamensprojekt.Fragments.BrugerFragment;
import com.example.eksamensprojekt.Fragments.ChatsFragment;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.model.Bruger;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler

    TextView fornavn;
    ShapeableImageView profilBillede;

    FirebaseUser firebaseBruger;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth = FirebaseAuth.getInstance();

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
                startActivity(new Intent(ChatActivity.this, VisProfilActivity.class));
                finish();

            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChatActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChatActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action Bar ^

        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser(); //TODO trail for at være sikker på den kommer derned
        final String bruger_id;
        String data = getIntent().getStringExtra("brugerid");
        if (data == null) {
            bruger_id = firebaseBruger.getUid();
        } else {
            bruger_id = getIntent().getStringExtra("brugerid");
        }

        fornavn = findViewById(R.id.fornavn);
        profilBillede = findViewById(R.id.profile_billede);

        firebaseBruger = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Brugere").child(bruger_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bruger bruger = dataSnapshot.getValue(Bruger.class);
                fornavn.setText(bruger.getFornavn());

//TODO            profile_billede.setImageResource(R.mipmap.ic_launcher);

         /*   if (bruger.getBilledeURL().equals("default")){
                profile_billede.setImageResource(R.mipmap.ic_launcher);
            } else {
                //TODO why "change this"
                Glide.with(getApplicationContext()).load(bruger.getBilledeURL()).into(profile_billede);
                } */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_paper);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ChatsFragment(), "Beskeder"); //Beskeder tab i chat aktiviteten
        viewPagerAdapter.addFragment(new BrugerFragment(), "Brugere"); //Brugere tab i chat aktiviteten

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //_________________________________
        firebaseBruger = firebaseAuth.getCurrentUser();
        // check if user is null
        if (firebaseBruger == null) {

            Intent ikkeLoggetIndIntent = new Intent(ChatActivity.this, OpretBrugerActivity.class);
            startActivity(ikkeLoggetIndIntent);
            finish();
        }
        // ^ Det her når ikke at køre ^
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titler;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titler = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titler.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titler.get(position);
        }
    }
}
