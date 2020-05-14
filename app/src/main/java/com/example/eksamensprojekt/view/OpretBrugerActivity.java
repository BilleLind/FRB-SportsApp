package com.example.eksamensprojekt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OpretBrugerActivity extends AppCompatActivity {

    private TextInputLayout mFuldeNavn;
    private TextInputLayout mEmail;
    private TextInputLayout mAdgangskode;
    private Button mBekraeftBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();

        mFuldeNavn = (TextInputLayout) findViewById(R.id.angivFuldeNavn);
        mEmail = (TextInputLayout) findViewById(R.id.angivEmail);
        mAdgangskode = (TextInputLayout) findViewById(R.id.angivTelefonNr);
        mBekraeftBtn = (Button) findViewById(R.id.bekraeftRegBtn);

        mBekraeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fulde_navn = mFuldeNavn.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String adgangskode = mAdgangskode.getEditText().getText().toString();

                registerNyBruger(fulde_navn, email, adgangskode);


            }
        });
    }

    private void registerNyBruger(String fulde_navn, String email, String adgangskode) {

        mAuth.createUserWithEmailAndPassword(email,adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent bookingIntent = new Intent(OpretBrugerActivity.this, BookTidActivity.class);
                    startActivity(bookingIntent);
                    finish();

                }else {
                    Toast.makeText(OpretBrugerActivity.this, "Der opstod en fejl", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}
