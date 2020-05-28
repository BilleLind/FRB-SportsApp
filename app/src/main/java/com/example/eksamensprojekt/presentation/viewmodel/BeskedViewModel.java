package com.example.eksamensprojekt.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eksamensprojekt.data.model.Besked;
import com.example.eksamensprojekt.repository.BeskedRepository;

public class BeskedViewModel extends AndroidViewModel {
    private BeskedRepository beskedRepository;
    LiveData<Besked> nyChatBeskedLiveData;
    LiveData<Besked> modtagChatBeskedLiveData;

    public BeskedViewModel(@NonNull Application application) {
        super(application);
        beskedRepository = new BeskedRepository();
    }


    public void nyBesked(Besked nyBesked) { //burde ikke være public, måske læg den i den samme mappe for at gøre det?
        nyChatBeskedLiveData = beskedRepository.sendNyBesked(nyBesked);
    }

    public void  viderSendBeskeder() {
        modtagChatBeskedLiveData = beskedRepository.modtagBesked();
    }

}
