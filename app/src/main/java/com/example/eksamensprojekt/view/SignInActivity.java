package com.example.eksamensprojekt.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

    }
}
