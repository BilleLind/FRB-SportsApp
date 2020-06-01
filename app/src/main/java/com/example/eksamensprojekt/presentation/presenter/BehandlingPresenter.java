package com.example.eksamensprojekt.presentation.presenter;

public class BehandlingPresenter {

    private String varighed,pris;

    public BehandlingPresenter(){
    }

    public BehandlingPresenter(String varighed, String pris) {
        this.varighed = varighed;
        this.pris = pris;
    }

    public String getVarighed() {
        return varighed;
    }

    public void setTid(){
        this.varighed = varighed;
    }

    public String getPris(){
        return pris;
    }

    public void setPris(){
        this.pris = pris;
    }


}
