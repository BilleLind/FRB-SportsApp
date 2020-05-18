package com.example.eksamensprojekt.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginBrugerActivity extends AppCompatActivity {

    private TextInputLayout iEmail;
    private TextInputLayout iAdgangskode;


    private Button mGoToOpretBrugerBtn;
    private Button mLoginbtn;
    private Button mStaticBtn;

    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bruger);

        mAuth = FirebaseAuth.getInstance();

        //Tilføjer custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        mLoginProgress = new ProgressDialog(this);

        mGoToOpretBrugerBtn = (Button) findViewById(R.id.goto_opret_bruger_btn);
        mLoginbtn = (Button) findViewById(R.id.login_paa_bruger);
        mStaticBtn = (Button) findViewById(R.id.static_goto_login_btn);


        //TextInput for login xml
        iEmail = (TextInputLayout) findViewById(R.id.indsaetEmail);
        iAdgangskode = (TextInputLayout) findViewById(R.id.indsaetAdgangskode);


        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String indsatEmail = iEmail.getEditText().getText().toString();
                String indsatAdgangskode = iAdgangskode.getEditText().getText().toString();

                if (!TextUtils.isEmpty(indsatEmail) || !TextUtils.isEmpty(indsatAdgangskode)) {

                    mLoginProgress.setTitle("Logger ind");
                    mLoginProgress.setMessage("Vent venligst mens vi finder din bruger.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    hentBruger(indsatEmail, indsatAdgangskode);

                }

            }
        });

        mGoToOpretBrugerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.activity_opret_bruger);

            }
        });

    }

    private void hentBruger(String indsatEmail, String indsatAdgangskode) {

        mAuth.signInWithEmailAndPassword(indsatEmail, indsatAdgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){


                    mLoginProgress.dismiss();
                    Intent mainIntent = new Intent(LoginBrugerActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {

                    mLoginProgress.hide();
                    Toast.makeText(LoginBrugerActivity.this, "Der opstod en fejl. Email eller adgangskode er forkert", Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}

