package com.example.eksamensprojekt.presentation.Interface;

import java.util.List;

public interface AlleBehandlingerLoadListener {

    void onAllBehandlingerLoadSucces(List<String> behandlingsTyperList);
    void onAllBehandlingerLoadFail(String message);
}
