package com.example.eksamensprojekt.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.eksamensprojekt.data.model.Besked;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BeskedRepository {

    private FirebaseAuth validering = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private Collection

    public MutableLiveData<List<Besked>> anmodBesked() {
        final MutableLiveData<List<Besked>> mutableLiveData = new MutableLiveData<>();



    }


    //https://medium.com/firebase-tips-tricks/how-to-create-a-clean-firebase-authentication-using-mvvm-37f9b8eb7336
    //https://github.com/alexmamo/FirebaseAuthentication/blob/master/app/src/main/java/ro/alexmamo/firebaseauthapp/splash/SplashRepository.java
    //https://firebase.google.com/docs/reference/android/com/google/firebase/database/DatabaseReference
    //https://firebase.google.com/docs/reference/android/com/google/firebase/firestore/CollectionReference
}
