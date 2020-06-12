package com.example.eksamensprojekt.presentation.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private TextInputLayout email, adgangskode;

    private Button goToOpretBrugerBtn, loginBtn;

    private ImageView actionBarMain,actionBarChat,actionBarProfil;

    private ProgressDialog loginProgress;

    //firebase authentication
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bruger);

        firebaseAuth = FirebaseAuth.getInstance();

        //Tilføjer custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        actionBarMain = (ImageView) findViewById(R.id.action_bar_logo);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);


        actionBarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginBrugerActivity.this, MainActivity.class));
                finish();
            }
        });

        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginBrugerActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginBrugerActivity.this, ChatActivity.class));
                finish();
            }
        });

        // ^^action bar


        //Skaber new progress dialog
        loginProgress = new ProgressDialog(this);

        //Sætter ids til de korrekte views
        goToOpretBrugerBtn = (Button) findViewById(R.id.goto_opret_bruger_btn);
        loginBtn = (Button) findViewById(R.id.login_paa_bruger);

        email = (TextInputLayout) findViewById(R.id.indsaetEmail);
        adgangskode = (TextInputLayout) findViewById(R.id.indsaetAdgangskode);


        //Tjekker om felterne er udfyldt og  henter bruger
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String indsatEmail = email.getEditText().getText().toString();
                String indsatAdgangskode = adgangskode.getEditText().getText().toString();

                if (!TextUtils.isEmpty(indsatEmail) || !TextUtils.isEmpty(indsatAdgangskode)) {

                    loginProgress.setTitle("Logger ind");
                    loginProgress.setMessage("Vent venligst mens vi finder din bruger.");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();

                    hentBruger(indsatEmail, indsatAdgangskode);

                }

            }
        });

        //skifter til opret bruger activity
        goToOpretBrugerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginBrugerActivity.this, OpretBrugerActivity.class));
                finish();

            }
        });

    }

    //metode der henter eksisterende bruger med email og adgangskode via firebase
    private void hentBruger(String indsatEmail, String indsatAdgangskode) {

        firebaseAuth.signInWithEmailAndPassword(indsatEmail, indsatAdgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){


                    loginProgress.dismiss();
                    Intent mainIntent = new Intent(LoginBrugerActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {

                    loginProgress.hide();
                    Toast.makeText(LoginBrugerActivity.this, "Der opstod en fejl. Email eller adgangskode er forkert", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}




