package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eksamensprojekt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.Objects;

public class StartTraeningActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView titleTextView;
    private WebView oovelseWebViewVar;
    private Button naesteOovelseButton, gennemfoortButton;
    private ImageView actionBarProfil, actionBarChat, actionBarMenu; //Action Bar Variabler
    private String webViewURL = "https://exorlive.com/video/?culture=da-DK&ex=11";
    private String webViewName;
    private int oovelsesPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_traening);

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
        actionBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartTraeningActivity.this, MainActivity.class));
                finish();
            }
        });
        // ^ Action bar ^

        final ArrayList oovelsesListe = new ArrayList(); //Primitiv liste over øvelser
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=11");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=605");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=711");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=29");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=16");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=8820");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=10306");

        final ArrayList oovelsesNavnListe = new ArrayList(); //Primitiv liste over navn på øvelser
        oovelsesNavnListe.add("Liggende bækkenløft");
        oovelsesNavnListe.add("Etbens knæbøj");
        oovelsesNavnListe.add("Bækkenløft m/knæstræk");
        oovelsesNavnListe.add("Armstræk");
        oovelsesNavnListe.add("Mavebøjning");
        oovelsesNavnListe.add("Lateral lunge");
        oovelsesNavnListe.add("Hoppende knæbøjninger");

        naesteOovelseButton = (Button)findViewById(R.id.naeste_Oovelse_Button);
        titleTextView = (TextView) findViewById(R.id.title_Text_View);

        //Skifter til ny øvelse
        naesteOovelseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oovelsesPosition <= 4) {
                    oovelsesPosition = oovelsesPosition + 1;
                } else {
                    Toast.makeText(getApplicationContext(),"Du har gennemført dagens program, godt klaret!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(StartTraeningActivity.this, FeedbackActivity.class));
                    finish();
                }
                webViewURL = (String) oovelsesListe.get(oovelsesPosition);
                webViewName = (String) oovelsesNavnListe.get(oovelsesPosition);
                titleTextView.setText(webViewName);
                oovelseWebViewVar.loadUrl(webViewURL);
            }
        });

        oovelseWebViewVar = (WebView)findViewById(R.id.oovelse_Web_View);
        oovelseWebViewVar.getSettings().setJavaScriptEnabled(true);
        oovelseWebViewVar.loadUrl(webViewURL);

        firebaseAuth = FirebaseAuth.getInstance();
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
