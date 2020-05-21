package com.example.eksamensprojekt.model;

public class Bruger {

    private String brugerId, fornavn, billedeURL;

    public Bruger(String id, String brugerNavn, String billedeURL) {
        this.brugerId = id;
        this.fornavn = brugerNavn;
        this.billedeURL = billedeURL;
    }

    public Bruger() {
    }

    public String getBrugerId() {
        return brugerId;
    }

    public void setBrugerId(String brugerId) {
        this.brugerId = brugerId;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getBilledeURL() {
        return billedeURL;
    }

    public void setBilledeURL(String billedeURL) {
        this.billedeURL = billedeURL;
    }
}
