package com.example.eksamensprojekt.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class OpretOgLogInActivity extends AppCompatActivity {

    private TextInputLayout mFuldeNavn;
    private TextInputLayout mEmail;
    private TextInputLayout mTelefonNr;
    private TextInputLayout mAdgangskode;
    private TextInputLayout iEmail;
    private TextInputLayout iAdgangskode;

    private Button mBekraeftBtn;
    private Button mGotoLogin;
    private Button mGoToOpretBruger;
    private Button iLoginPaaBruger;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //TextInput for opret ny bruger xml
        mFuldeNavn = (TextInputLayout) findViewById(R.id.angivFuldeNavn);
        mEmail = (TextInputLayout) findViewById(R.id.angivEmail);
        mAdgangskode = (TextInputLayout) findViewById(R.id.angivAdgangskode);
        mTelefonNr = (TextInputLayout) findViewById(R.id.angivTelefonNr);

        //TextInput for log in xml
        iEmail = (TextInputLayout) findViewById(R.id.indsaetEmail);
        iAdgangskode = (TextInputLayout) findViewById(R.id.indsaetAdgangskode);



        //Setting up button to correct ids
        mBekraeftBtn = (Button) findViewById(R.id.bekraeft_ny_bruger_btn);
        mGotoLogin = (Button) findViewById(R.id.goto_loginin_btn);
        mGoToOpretBruger = (Button) findViewById(R.id.goto_opret_bruger_btn);
        iLoginPaaBruger = (Button) findViewById(R.id.login_btn);

        mBekraeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fuldeNavn = mFuldeNavn.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String telefonNr = mTelefonNr.getEditText().getText().toString();
                String adgangskode = mAdgangskode.getEditText().getText().toString();

                registerNyBruger(email, adgangskode);


            }
        });

        mGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_login_in);
            }
        });

        mGoToOpretBruger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.activity_opret_bruger);
            }
        });
    }

    private void registerNyBruger( String email, String adgangskode) {

        mAuth.createUserWithEmailAndPassword(email,adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent bookingIntent = new Intent(OpretOgLogInActivity.this, BookTidActivity.class);
                    startActivity(bookingIntent);
                    finish();

                }else {
                    Toast.makeText(OpretOgLogInActivity.this, "Der opstod en fejl", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}
