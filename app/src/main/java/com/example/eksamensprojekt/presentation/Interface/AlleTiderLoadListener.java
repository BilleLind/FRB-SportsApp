package com.example.eksamensprojekt.presentation.Interface;


import com.example.eksamensprojekt.presentation.presenter.TiderPresenter;

import java.util.List;

public interface AlleTiderLoadListener {

    void onAllTiderLoadSucces(List<TiderPresenter> tiderList);
    void onAllTiderLoadFail(String message);
}
