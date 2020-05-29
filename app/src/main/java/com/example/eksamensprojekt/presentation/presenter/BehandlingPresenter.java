package com.example.eksamensprojekt.presentation.presenter;

public class BehandlingPresenter {

    private String tid,pris;

    public BehandlingPresenter(){
    }

    public BehandlingPresenter(String tid, String pris) {
        this.tid = tid;
        this.pris = pris;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(){
        this.tid = tid;
    }

    public String getPris(){
        return pris;
    }

    public void setPris(){
        this.pris = pris;
    }


}
