package com.example.eksamensprojekt.presentation.presenter;

public class AnsattePresenter {
    /**
     * @author Anders, Sebastian og Marc
     * @version 1.2
     */

    private String navn;

    public AnsattePresenter(){

    }

    public AnsattePresenter(String navn){
        this.navn = navn;
    }

    public String getNavn(){
        return navn;
    }

    public void setNavn(){

        this.navn = navn;
    }
}
