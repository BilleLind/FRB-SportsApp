package com.example.eksamensprojekt.presentation.view;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import java.util.Objects;

import static com.example.eksamensprojekt.presentation.Interface.Konstante.brugere;

public class OpretBrugerActivity extends AppCompatActivity {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private TextInputLayout fornavn, efternavn, email, telefonNr, adgangskode;

    private ImageView actionBarHome,actionBarChat,actionBarProfil;
    private Button bekraeftBtn, goToLoginBtn;

    private ProgressDialog opretProgress;


    //firebase authentication
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        firebaseAuth = firebaseAuth.getInstance();


        //Tilføjer custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        actionBarHome = (ImageView) findViewById(R.id.action_bar_home);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);


        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OpretBrugerActivity.this, MainActivity.class));
                finish();
            }
        });

        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OpretBrugerActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OpretBrugerActivity.this, ChatActivity.class));
                finish();
            }
        });


        // ^^action bar


        //Skaber en ny progress dialog
        opretProgress = new ProgressDialog(this);

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

            opretProgress.setTitle("Opretter bruger");
            opretProgress.setMessage("Vent venligst mens vi opretter din bruger.");
            opretProgress.setCanceledOnTouchOutside(false);
            opretProgress.show();

            opretBruger(email, adgangskode, fornavn, efternavn, telefonNr);
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



    //Metode til registrering af ny bruger gennem firebase
    private void opretBruger(final String email, String adgangskode, final String fornavn, final String efternavn, final String telefonNr) {

        firebaseAuth.createUserWithEmailAndPassword(email, adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    final String brugerid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child(brugere).child(brugerid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", brugerid);
                    hashMap.put("fornavn", fornavn);
                    hashMap.put("efternavn", efternavn);
                    hashMap.put("telefonNr", telefonNr);
                    hashMap.put("email", email);
                    hashMap.put("brugerType", "patient");
                    hashMap.put("billedeURL", "null");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                opretProgress.dismiss();
                                Intent intent = new Intent(OpretBrugerActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(OpretBrugerActivity.this, "Oprettelse af bruger gennemført", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });


                } else {

                    opretProgress.hide();
                    Toast.makeText(OpretBrugerActivity.this, "Der opstod en fejl. Check felterne for fejl og prøv igen", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}

