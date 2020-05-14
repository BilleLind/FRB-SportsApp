package com.example.eksamensprojekt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        mAuth = FirebaseAuth.getInstance();

        Button opretBruger = findViewById(R.id.opretBtn);
        opretBruger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OpretBrugerActivity.class);
                startActivity(i);
            }
        });

    }
}
