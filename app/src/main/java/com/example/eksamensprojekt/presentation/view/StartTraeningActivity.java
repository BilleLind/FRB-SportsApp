package com.example.eksamensprojekt.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    private String webViewURL = "https://exorlive.com/video/?culture=da-DK&ex=12498";
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
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=12492");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=12493");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=12494");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=12495");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=12496");
        oovelsesListe.add("https://exorlive.com/video/?culture=da-DK&ex=12497");

        naesteOovelseButton = (Button)findViewById(R.id.naeste_Oovelse_Button);

        //Skifter til ny øvelse
        naesteOovelseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewURL = (String) oovelsesListe.get(oovelsesPosition);
                oovelseWebViewVar.loadUrl(webViewURL);
                if (oovelsesPosition <= 4) {
                    oovelsesPosition = oovelsesPosition + 1;
                } else{
                    startActivity(new Intent(StartTraeningActivity.this, FeedbackActivity.class));
                    finish();
                }
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
