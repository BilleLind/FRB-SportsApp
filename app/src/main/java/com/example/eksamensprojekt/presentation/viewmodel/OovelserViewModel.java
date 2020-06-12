package com.example.eksamensprojekt.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eksamensprojekt.data.model.Oovelser;
import com.example.eksamensprojekt.data.repository.OovelserRepository;

import java.util.List;

public class OovelserViewModel extends ViewModel { //Extender ViewModel
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */
    private MutableLiveData<List<Oovelser>> mOovelser; //MutableLiveData er en subclass af LiveData. Mutable LiveData kan ændres, det kan LiveData ikke.
    private OovelserRepository mRepository;

    public void init() {
        if (mOovelser != null) { //Hvis vi allerede har hentet dataen
            return;
        }
        mRepository = OovelserRepository.getInstance(); //Får fat i instance fra repository
        mOovelser = mRepository.getOovelser(); //Henter MutableLiveData listen fra repo
    }

    public LiveData<List<Oovelser>> getOovelser() { //LiveData kan kun observeres, ikke ændres. Vi bruger derfor MutableLiveData til at ændre det indirekte hvor vi får brug for det.
        return mOovelser;
    }
}
