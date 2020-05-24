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
import com.example.eksamensprojekt.model.Bruger;
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

public class OpretBrugerActivity extends AppCompatActivity {

    private TextInputLayout mFornavn;
    private TextInputLayout mEfternavn;
    private TextInputLayout mEmail;
    private TextInputLayout mTelefonNr;
    private TextInputLayout mAdgangskode;

    private Button mBekraeftBtn;
    private Button mGotoLoginBtn;

    private ProgressDialog mRegProgress;

    //firebase authentication
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();

        //Tilføjer custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Skaber new progress dialog
        mRegProgress = new ProgressDialog(this);

        //sætter ids op til de korrekte views
        mFornavn = (TextInputLayout) findViewById(R.id.fornavn);
        mEfternavn = (TextInputLayout) findViewById(R.id.angivEfternavn);
        mEmail = (TextInputLayout) findViewById(R.id.angivEmail);
        mAdgangskode = (TextInputLayout) findViewById(R.id.angivAdgangskode);
        mTelefonNr = (TextInputLayout) findViewById(R.id.angivTelefonNr);


        mBekraeftBtn = (Button) findViewById(R.id.bekraeft_ny_bruger_btn);
        mGotoLoginBtn = (Button) findViewById(R.id.goto_loginin_btn);


        //Tager inputs og registere ny bruger
        mBekraeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fornavn = mFornavn.getEditText().getText().toString();
                String efternavn = mEfternavn.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String telefonNr = mTelefonNr.getEditText().getText().toString();
                String adgangskode = mAdgangskode.getEditText().getText().toString();


                //if statement: hvis nogle af felterne er tomme, bliver oprettelsen brudt.
                if (!TextUtils.isEmpty(fornavn) || !TextUtils.isEmpty(efternavn) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(telefonNr) || !TextUtils.isEmpty(adgangskode)) {


                    mRegProgress.setTitle("Opretter bruger");
                    mRegProgress.setMessage("Vent venligst mens vi opretter din bruger.");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    opretBruger(email, adgangskode, fornavn, efternavn, telefonNr);
                }

            }
        });


        //Skifter til login in activity
        mGotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OpretBrugerActivity.this, LoginBrugerActivity.class));
                finish();

            }
        });

    }


    //Metode til registrering af ny bruger gennem firebase
    private void opretBruger(final String email, String adgangskode, final String fornavn, final String efternavn, final String telefonNr) {

        mAuth.createUserWithEmailAndPassword(email, adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    final String brugerid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Brugere").child(brugerid); //TODO does this fix it? error in brugerFragment?

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", brugerid);
                    hashMap.put("fornavn", fornavn);
                    hashMap.put("efternavn", efternavn);
                    hashMap.put("telefonNr", telefonNr);
                    hashMap.put("email", email);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mRegProgress.dismiss();
                                Intent intent = new Intent(OpretBrugerActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(OpretBrugerActivity.this, "Oprettelse af bruger gennemført", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });


                } else {

                    mRegProgress.hide();
                    Toast.makeText(OpretBrugerActivity.this, "Der opstod en fejl. Check felterne for fejl og prøv igen", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

