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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OpretBrugerActivity extends AppCompatActivity {

    private TextInputLayout mFuldeNavn;
    private TextInputLayout mEmail;
    private TextInputLayout mAdgangskode;
    private Button mBekraeftBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_bruger);

        mAuth = FirebaseAuth.getInstance();

        mFuldeNavn = (TextInputLayout) findViewById(R.id.reg_FuldeNavn);
        mEmail = (TextInputLayout) findViewById(R.id.reg_Email);
        mAdgangskode = (TextInputLayout) findViewById(R.id.reg_Adgangskode);
        mBekraeftBtn = (Button) findViewById(R.id.bekraeftRegBtn);

        mBekraeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brugerNavn = mFuldeNavn.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String adgangskode = mAdgangskode.getEditText().getText().toString();

                registerNyBruger(brugerNavn, email, adgangskode);


            }
        });
    }

    private void registerNyBruger(final String brugerNavn, String email, String adgangskode) {

        mAuth.createUserWithEmailAndPassword(email, adgangskode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id" , userid);
                    hashMap.put("brugerNavn", brugerNavn);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(OpretBrugerActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }else {
                    Toast.makeText(OpretBrugerActivity.this, "Der opstod en fejl", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}
