package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Oovelser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class StartTraeningActivity extends AppCompatActivity {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private FirebaseAuth firebaseAuth;

    private TextView titleTextView;
    private WebView oovelseWebViewVar;
    private Button naesteOovelseButton;
    private ImageView actionBarProfil, actionBarChat, actionBarHome; //Action Bar Variabler
    private String webViewURL = "https://exorlive.com/video/?culture=da-DK&ex=11";
    private String webViewName;
    private int oovelsesPosition = 0;

    public ArrayList<Oovelser> oovelsesListe = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_traening);

        initOovelser(); //Initialiserer træningsprogram

        //Action Bar
        //Tilføjer custom action bar til activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        //Forbinder IDs til de korrekte views
        actionBarProfil = findViewById(R.id.action_bar_profil);
        actionBarChat = findViewById(R.id.action_bar_chat);
        actionBarHome = findViewById(R.id.action_bar_home);

        //Skifter til vis profil activity
        actionBarProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartTraeningActivity.this, VisProfilActivity.class));
                finish();
            }
        });

        //Skifter til chat activity
        actionBarChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartTraeningActivity.this, ChatActivity.class));
                finish();
            }
        });

        //Skifter til menu activity
        actionBarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartTraeningActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        naesteOovelseButton = findViewById(R.id.naeste_Oovelse_Button);
        titleTextView = findViewById(R.id.title_Text_View);
        oovelseWebViewVar = findViewById(R.id.oovelse_Web_View);
        oovelseWebViewVar.getSettings().setJavaScriptEnabled(true);
        oovelseWebViewVar.loadUrl(webViewURL);

        //Skifter til ny øvelse
        naesteOovelseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tjekOmFærdig();
                naesteOovelse();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void tjekOmFærdig() {
        oovelsesPosition = oovelsesPosition + 1;
        if (oovelsesPosition >= oovelsesListe.size()) { //Denne if statemenet tjekker om der er flere øvelser tilbage i arraylisten/træningsprogrammet
            Toast.makeText(getApplicationContext(), "Du har gennemført dagens program, godt klaret!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(StartTraeningActivity.this, FeedbackActivity.class)); //Hvis ikke bliver brugeren sendt videre til feedback menuen
            finish();
        }
    }

    public void initOovelser() {

        //Primitiv liste over øvelser
        oovelsesListe.add(new Oovelser("Liggende bækkenløft", "https://exorlive.com/video/?culture=da-DK&ex=11", "https://media.exorlive.com/?id=11&filetype=jpg&env=production"));
        oovelsesListe.add(new Oovelser("Etbens knæbøj", "https://exorlive.com/video/?culture=da-DK&ex=605", "https://media.exorlive.com/?id=605&filetype=jpg&env=production"));
        oovelsesListe.add(new Oovelser("Bækkenløft m/knæstræk", "https://exorlive.com/video/?culture=da-DK&ex=711", "https://media.exorlive.com/?id=711&filetype=jpg&env=production"));
        oovelsesListe.add(new Oovelser("Armstræk", "https://exorlive.com/video/?culture=da-DK&ex=29", "https://media.exorlive.com/?id=29&filetype=jpg&env=production"));
        oovelsesListe.add(new Oovelser("Mavebøjning", "https://exorlive.com/video/?culture=da-DK&ex=16", "https://media.exorlive.com/?id=16&filetype=jpg&env=production"));
        oovelsesListe.add(new Oovelser("Lateral lunge", "https://exorlive.com/video/?culture=da-DK&ex=8820", "https://media.exorlive.com/?id=8820&filetype=jpg&env=production"));
        oovelsesListe.add(new Oovelser("Hoppende knæbøjninger", "https://exorlive.com/video/?culture=da-DK&ex=10306", "https://media.exorlive.com/?id=10306&filetype=jpg&env=production"));
    }

    public void naesteOovelse() {
        if (oovelsesPosition < oovelsesListe.size()) { //Denne if statement stopper appen fra at crashe hvis den løber tør for indexes i Arraylisten
            //Skifter øvelses navnet
            webViewName = (String) oovelsesListe.get(oovelsesPosition).getName();
            titleTextView.setText(webViewName);
            //Skifter øvelses videoen
            webViewURL = (String) oovelsesListe.get(oovelsesPosition).getVideoURL();
            oovelseWebViewVar.loadUrl(webViewURL);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);

        if (currentUser == null) {

            Intent signInIntent = new Intent(StartTraeningActivity.this, OpretBrugerActivity.class);
            startActivity(signInIntent);
            finish();
        }
    }
}
