package com.example.eksamensprojekt.repository;


import androidx.lifecycle.MutableLiveData;
import com.example.eksamensprojekt.data.model.Besked;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.List;

import static com.example.eksamensprojekt.utils.Konstante.brugerId;
import static com.example.eksamensprojekt.utils.Konstante.brugere;
import static com.example.eksamensprojekt.utils.Konstante.chats;

public class BeskedRepository {

    private FirebaseAuth validering = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference(brugere);

    public MutableLiveData<Besked> sendNyBesked(Besked nybesked) { // had it in List, didn't work in ViewModel
        final MutableLiveData<Besked> nyBeskedMutableLiveData = new MutableLiveData<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("afsender", nybesked.getAfsender());
        hashMap.put("modtager", nybesked.getModtager());
        hashMap.put("besked", nybesked.getBesked());

        databaseReference.child(chats).push().setValue(hashMap);

        return nyBeskedMutableLiveData;
    }


}
