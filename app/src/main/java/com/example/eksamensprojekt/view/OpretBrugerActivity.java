package com.example.eksamensprojekt.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class OpretBrugerActivity extends AppCompatActivity {

    private TextInputLayout fornavn;
    private TextInputLayout efternavn;
    private TextInputLayout email;
    private TextInputLayout telefonNr;
    private TextInputLayout adgangskode;

    private Button bekraeftBtn;
    private Button goToLoginBtn;

    private ProgressDialog regProgress;

    private ImageView actionBarMain;

    //Deklarere en instans af firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        firebaseAuth = firebaseAuth.getInstance();


        //Tilføjer custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        actionBarMain = (ImageView) findViewById(R.id.action_bar_logo);

        actionBarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OpretBrugerActivity.this, MainActivity.class));
                finish();
            }
        });

        // ^^action bar


        //Skaber en ny progress dialog
        regProgress = new ProgressDialog(this);

        //sætter ids til de korrekte views
        fornavn = (TextInputLayout) findViewById(R.id.angivFornavn);
        efternavn = (TextInputLayout) findViewById(R.id.angivEfternavn);
        email = (TextInputLayout) findViewById(R.id.angivEmail);
        adgangskode = (TextInputLayout) findViewById(R.id.angivAdgangskode);
        telefonNr = (TextInputLayout) findViewById(R.id.angivTelefonNr);


        bekraeftBtn = (Button) findViewById(R.id.bekraeft_ny_bruger_btn);
        goToLoginBtn = (Button) findViewById(R.id.goto_loginin_btn);


        //På button click, kør bekraeft bruger metode
        bekraeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bekraeftBruger();

            }
        });


        //Skifter til login in activity
        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OpretBrugerActivity.this, LoginBrugerActivity.class));
                finish();

            }
        });

    }



    //Metoden til at bekræfte den indtastede data er gyldig
    private void bekraeftBruger() {

        String fornavn = OpretBrugerActivity.this.fornavn.getEditText().getText().toString();
        String efternavn = OpretBrugerActivity.this.efternavn.getEditText().getText().toString();
        String email = OpretBrugerActivity.this.email.getEditText().getText().toString();
        String telefonNr = OpretBrugerActivity.this.telefonNr.getEditText().getText().toString();
        String adgangskode = OpretBrugerActivity.this.adgangskode.getEditText().getText().toString();

        //Tjekker om det indtastede data overholder regler. Hvis ikke, oprettelsen brudt.
        if (tjekFornavn(fornavn) != true || tjekEfternavn(efternavn) != true || tjekEmail(email) != true || tjekTelefonNr(telefonNr) != true || tjekAdgangskode(adgangskode) != true){


            regProgress.setTitle("Opretter bruger");
            regProgress.setMessage("Vent venligst mens vi opretter din bruger.");
            regProgress.setCanceledOnTouchOutside(false);
            regProgress.show();

            opretBruger(email, adgangskode);
        }

    }

    //Tjekker om adgangskode indeholde mindst 8 tegn. Bestående af tal + store og små bogstaver.
    private boolean tjekAdgangskode(String adgangskode) {

        boolean godkendtAdgangskode;

        if (adgangskode.length() < 8){
            godkendtAdgangskode = false;
            //<<exception>> forLidtTegnException()

        }else if (manglerStortLilleBogstavEllerTal(adgangskode) == false){
            godkendtAdgangskode = false;
            //<<exception>> manglerStortLilleBogstavEllerTalException()

        }else {
            godkendtAdgangskode = true;
        }
        return godkendtAdgangskode;
    }

    //Metode til at gennemgå hvert enkel tegn i adgangskoden
    private boolean manglerStortLilleBogstavEllerTal(String adgangskode) {
        char ch;
        boolean stortBogstav = false;
        boolean lilleBogstav = false;
        boolean tal = false;

        for(int i=0; i < adgangskode.length();i++) {
            ch = adgangskode.charAt(i);
            if( Character.isDigit(ch)) {
                tal = true;
            }
            else if (Character.isUpperCase(ch)) {
                stortBogstav = true;
            } else if (Character.isLowerCase(ch)) {
                lilleBogstav = true;
            }
            if(tal && stortBogstav && lilleBogstav)
                return true;
        }
        return false;
    }


    //Tjekker om telefonnummeret er større eller mindre end 8
    private boolean tjekTelefonNr(String telefonNr) {

        boolean godkendtTelefonNr;

        int nummer = Integer.parseInt(telefonNr);

        if (nummer < 8){
            godkendtTelefonNr = false;
            //<<exception>> forKortException()

        }else if (nummer > 8){
            godkendtTelefonNr = false;
            //<<exception>> forLangtException()

        }else {
            godkendtTelefonNr = true;
        }

        return godkendtTelefonNr;
    }

    //Tjekker om email indeholder "@" eller "."
    private boolean tjekEmail(String email) {

        boolean godkendtEmail = false;

        if (email.contains("@") || email.contains(".")){
            godkendtEmail = true;
            //<<exception>> ManglerSnabelAEllerPunktumException()

        }
        return godkendtEmail;
    }

    //Tjekker om efternavn er tomt eller indeholder tal
    private boolean tjekEfternavn(String efternavn) {

        boolean godkendtEfternavn;

        if (efternavn == null){
            godkendtEfternavn = false;
            //<<exception>> TomtFeltException()

        }else if (efternavn.matches(".*\\d.*")){
            godkendtEfternavn = false;
            //<<exception>> IndeholderTalException()

        }else{
            godkendtEfternavn = true;
        }
        return godkendtEfternavn;

    }

    //Tjekker om fornavn er tomt eller tal
    private boolean tjekFornavn(String fornavn) {

        boolean godkendtFornavn;

        if (fornavn == null){
            godkendtFornavn = false;
            //<<exception>> TomtFeltException()

        }else if (fornavn.matches(".*\\d.*")){
            godkendtFornavn = false;
            //<<exception>> IndeholderTalException()

        }else{
            godkendtFornavn = true;
        }
        return godkendtFornavn;
    }


    //Metode til registrering af ny bruger gennem firebase, som forbinder email og adgangskode til brugeren.
    private void opretBruger(String email, String adgangskode) {

        firebaseAuth.createUserWithEmailAndPassword(email,adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    regProgress.dismiss();

                    Intent visProfilIntet = new Intent(OpretBrugerActivity.this, VisProfilActivity.class);
                    startActivity(visProfilIntet);
                    Toast.makeText(OpretBrugerActivity.this, "Oprettelse af bruger gennemført", Toast.LENGTH_LONG).show();
                    finish();

                }else {

                    regProgress.hide();
                    Toast.makeText(OpretBrugerActivity.this, "Der opstod en fejl. Check felterne for fejl og prøv igen", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
