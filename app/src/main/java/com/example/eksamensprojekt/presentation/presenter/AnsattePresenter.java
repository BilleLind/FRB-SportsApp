package com.example.eksamensprojekt.presentation.presenter;

public class AnsattePresenter {

    private String navn;

    public AnsattePresenter(){

    }

    public AnsattePresenter(String navn){
        this.navn = navn;
    }

    public String getNavn(){
        return navn;
    }

    public void setAnsatteNavn(){

        this.navn = navn;
    }
}
