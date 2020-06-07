package com.example.eksamensprojekt.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eksamensprojekt.data.model.Oovelser;
import com.example.eksamensprojekt.data.repository.OovelserOversigtRepository;

import java.util.List;

public class IntroTilOovelserViewModel extends ViewModel { //Extender ViewModel

    private MutableLiveData<List<Oovelser>> mOovelser; //MutableLiveData er en subclass af LiveData. Mutable LiveData kan ændres, det kan LiveData ikke.
    private OovelserOversigtRepository mRepository;

    public void init() {
        if (mOovelser != null) {
            return;
        }
        mRepository = OovelserOversigtRepository.getInstance();
        mOovelser = mRepository.getOovelser();
    }

    public LiveData<List<Oovelser>> getOovelser() { //LiveData kan kun observeres, ikke ændres. Vi bruger derfor MutableLiveData til at ændre det indirekte hvor vi får brug for det.
        return mOovelser;
    }
}
