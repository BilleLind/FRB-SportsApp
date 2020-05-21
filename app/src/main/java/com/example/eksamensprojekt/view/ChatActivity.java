package com.example.eksamensprojekt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import com.google.android.material.appbar.MaterialToolbar;
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



    TextView fornavn;
    ShapeableImageView profile_billede;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Tilføjer custom action bar
      /*  Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout); */





    fornavn = findViewById(R.id.fornavnChat);
    profile_billede = findViewById(R.id.profile_billede);

    firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("Brugere").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
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

        viewPagerAdapter.addFragment(new ChatsFragment(), "Beskeder");
        viewPagerAdapter.addFragment(new BrugerFragment(), "Bruger");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);



}

static class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewPagerAdapter(FragmentManager fm) { //TODO iether find out how it works with viewpager2 or this that are coded now
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
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

    public void addFragment( Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

    public void tilbage(View view) { //TODO hurtig log ud knap inde i chatten hvor man ser brugere og beskeder
       FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ChatActivity.this, MainActivity.class));
        finish();
    }
}
