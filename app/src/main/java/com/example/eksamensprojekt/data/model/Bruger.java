package com.example.eksamensprojekt.data.model;

import com.google.firebase.database.Exclude;

public class Bruger {

    private String id, fornavn, billedeURL, brugerType, efternavn, telefonNr, email;


    public Bruger(String id, String fornavn, String billedeURL, String brugerType) {
        this.id = id;
        this.fornavn = fornavn;
        this.billedeURL = billedeURL;
        this.brugerType = brugerType;
    }
    public Bruger(String id, String fornavn) {
        this.id = id;
        this.fornavn = fornavn;
    }


    public Bruger() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBrugerType() {
        return brugerType;
    }

    public void setBrugerType(String brugerType) {
        this.brugerType = brugerType;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public String getTelefonNr() {
        return telefonNr;
    }

    public void setTelefonNr(String telefonNr) {
        this.telefonNr = telefonNr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

