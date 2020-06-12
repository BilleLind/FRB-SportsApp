package com.example.eksamensprojekt.presentation.presenter;

public class TiderPresenter {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

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