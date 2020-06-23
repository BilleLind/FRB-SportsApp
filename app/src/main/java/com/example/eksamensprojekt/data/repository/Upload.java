package com.example.eksamensprojekt.data.repository;

public class Upload {

    private String mbilledenavn; //m for at fortælle at den kun findes i denne klasse
    private String mbilledeURL;


    public Upload() {

    }

    public Upload(String billedenavn, String billedeURL) {
        if (billedenavn.trim().equals("")) {
            billedenavn = "ingen navn";
        }
        this.mbilledenavn = billedenavn;
        this.mbilledeURL = billedeURL;
    }
    public String getBilledeNavn() {
        return mbilledenavn;
    }

    public void  setBilledeNavn(String billedenavn) {
        this.mbilledenavn = billedenavn;
    }

    public String getBilledeURL() {
        return mbilledeURL;
    }

    public void setBilledeURL(String billedeURL) {
        this.mbilledeURL = billedeURL;
    }


}
