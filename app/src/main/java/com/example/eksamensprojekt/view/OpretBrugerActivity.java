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

public class OpretBrugerActivity extends AppCompatActivity {

    private TextInputLayout mFornavn;
    private TextInputLayout mEfternavn;
    private TextInputLayout mEmail;
    private TextInputLayout mTelefonNr;
    private TextInputLayout mAdgangskode;

    private Button mBekraeftBtn;
    private Button mGotoLoginBtn;

    private ProgressDialog mRegProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();

        //Tilføjer custom actionbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        mRegProgress = new ProgressDialog(this);



        //TextInput for opret ny bruger xml
        mFornavn = (TextInputLayout) findViewById(R.id.angivFornavn);
        mEfternavn = (TextInputLayout) findViewById(R.id.angivEfternavn);
        mEmail = (TextInputLayout) findViewById(R.id.angivEmail);
        mAdgangskode = (TextInputLayout) findViewById(R.id.angivAdgangskode);
        mTelefonNr = (TextInputLayout) findViewById(R.id.angivTelefonNr);

        //Setting up button to correct ids
        mBekraeftBtn = (Button) findViewById(R.id.bekraeft_ny_bruger_btn);
        mGotoLoginBtn = (Button) findViewById(R.id.goto_loginin_btn);


        //Tager angivet inputs og registere ny bruger
        mBekraeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fornavn = mFornavn.getEditText().getText().toString();
                String efternavn = mEfternavn.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String telefonNr = mTelefonNr.getEditText().getText().toString();
                String adgangskode = mAdgangskode.getEditText().getText().toString();

                if (!TextUtils.isEmpty(fornavn) || !TextUtils.isEmpty(efternavn) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(telefonNr) || !TextUtils.isEmpty(adgangskode)){


                    mRegProgress.setTitle("Registrere bruger");
                    mRegProgress.setMessage("Vent venligst mens vi opretter din bruger.");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    registerNyBruger(email, adgangskode);
                }

            }
        });


        //Skifter til login in layout
        mGotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.activity_login_bruger);

            }
        });

    }

    //Metode til registrering af ny bruger gennem firebase
    private void registerNyBruger( String email, String adgangskode) {

        mAuth.createUserWithEmailAndPassword(email,adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    mRegProgress.dismiss();

                    Intent visProfilIntet = new Intent(OpretBrugerActivity.this, VisProfilActivity.class);
                    startActivity(visProfilIntet);
                    Toast.makeText(OpretBrugerActivity.this, "Oprettelse af bruger gennemført", Toast.LENGTH_LONG).show();
                    finish();

                }else {

                    mRegProgress.hide();
                    Toast.makeText(OpretBrugerActivity.this, "Der opstod en fejl. Check felterne for fejl og prøv igen", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
