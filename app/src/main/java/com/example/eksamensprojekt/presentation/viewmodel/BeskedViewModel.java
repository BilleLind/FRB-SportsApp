package com.example.eksamensprojekt.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eksamensprojekt.data.model.Besked;
import com.example.eksamensprojekt.data.repository.BeskedRepository;

public class BeskedViewModel extends AndroidViewModel {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */
    private BeskedRepository beskedRepository;
    LiveData<Besked> nyChatBeskedLiveData;


    public BeskedViewModel(@NonNull Application application) {
        super(application);
        beskedRepository = new BeskedRepository();
    }


    public void nyBesked(Besked nyBesked) { //her forbindes Repositoriet og viewModel. hvor MutableLiveData bliver til LiveData, med nyBesked af Besked.
        nyChatBeskedLiveData = beskedRepository.sendNyBesked(nyBesked);
    }

}
