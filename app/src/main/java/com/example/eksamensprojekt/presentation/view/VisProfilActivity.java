package com.example.eksamensprojekt.presentation.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Besked;
import com.example.eksamensprojekt.data.model.Bruger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.eksamensprojekt.presentation.Interface.Konstante.VEALG_BILLEDE_ANMODNING;
import static com.example.eksamensprojekt.presentation.Interface.Konstante.brugere;


public class VisProfilActivity extends AppCompatActivity {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private Button brugerLogUdKnap;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseBruger;
    ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler
    ShapeableImageView profil_billede;
    Button skiftBillede, billedeValg;
    ProgressBar progressBar;
    private Uri billedeURI;
    private StorageReference opbevaringsRef;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis_profil);

        profil_billede = findViewById(R.id.profile_billede);
        billedeValg =findViewById(R.id.billedeValg);
        skiftBillede = findViewById(R.id.setBilledet);
        progressBar = findViewById(R.id.progress_billede);

        opbevaringsRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Forbinder IDs til de korrekte views
        actionBarProfil = (ImageView) findViewById(R.id.action_bar_profil);
        actionBarChat = (ImageView) findViewById(R.id.action_bar_chat);
        actionBarMenu = (ImageView) findViewById(R.id.action_bar_logo);

        //Skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisProfilActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        skiftBillede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaelgBillede();

            }
        });
        billedeValg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBillede();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        brugerLogUdKnap = (Button) findViewById(R.id.log_ud_btn);


        brugerLogUdKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VisProfilActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    //Tjekker om bruger er logget ind. Hvis ikke, bliver bruger præsenteret for opret bruger aktiviteten.
    @Override
    public void onStart() {
        super.onStart();

        // Tjek om bruger er logged in (ikke null) og opdater UI som nødvendigt.
        firebaseBruger = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);

        if (firebaseBruger == null) {

            Intent ikkeLoggetIndIntent = new Intent(VisProfilActivity.this, OpretBrugerActivity.class);
            startActivity(ikkeLoggetIndIntent);
            finish();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadBillede() {
        if (billedeURI !=null) {
            final StorageReference filReference = opbevaringsRef.child(firebaseBruger.getUid() + "." + getFileExtension(billedeURI));

            filReference.putFile(billedeURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 100);
                    Toast.makeText(VisProfilActivity.this, "Upload fuldført", Toast.LENGTH_SHORT).show();
                    String temp = taskSnapshot.getUploadSessionUri().toString();
                    databaseReference.child(brugere).child(firebaseBruger.getUid()).child("billedeURL").setValue(temp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(VisProfilActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(this, "Intet billede valgt", Toast.LENGTH_SHORT).show();
        }
    }


    private void vaelgBillede() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, VEALG_BILLEDE_ANMODNING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VEALG_BILLEDE_ANMODNING && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            billedeURI = data.getData();

            Picasso.get().load(billedeURI).into(profil_billede);
        }
    }
}
