package com.example.eksamensprojekt.presentation.presenter;

public class AnsattePresenter {

    private String ansatteNavn;

    public AnsattePresenter(){

    }

    public AnsattePresenter(String ansatteNavn){
        this.ansatteNavn = ansatteNavn;
    }

    public String getAnsatteNavn(){
        return ansatteNavn;
    }

    public void setAnsatteNavn(){
        this.ansatteNavn = ansatteNavn;
    }
}
