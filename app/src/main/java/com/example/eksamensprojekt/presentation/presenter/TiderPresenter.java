package com.example.eksamensprojekt.presentation.presenter;

public class TiderPresenter {

    private String tid;

    public TiderPresenter(){

    }

    public TiderPresenter(String tid){
        this.tid = tid;
    }

    public String getTid(){
        return tid;
    }

    public void setTid(){

        this.tid = tid;
    }
}