package com.example.eksamensprojekt.presentation.Interface;

import com.example.eksamensprojekt.presentation.presenter.AnsattePresenter;

import java.util.List;

public interface AlleAnsatteLoadListener {

    void onAllAnsatteLoadSucces(List<AnsattePresenter> ansatteList);
    void onAllAnsatteLoadFail(String message);
}
