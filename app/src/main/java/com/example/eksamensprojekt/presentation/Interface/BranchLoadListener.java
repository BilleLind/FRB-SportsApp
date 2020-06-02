package com.example.eksamensprojekt.presentation.Interface;

import com.example.eksamensprojekt.presentation.presenter.BehandlingPresenter;

import java.util.List;

public interface BranchLoadListener {

    void onBranchLoadSucces(List<BehandlingPresenter> behandlingsList);
    void onBranchLoadFail(String message);
}
