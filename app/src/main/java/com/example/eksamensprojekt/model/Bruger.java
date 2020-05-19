package com.example.eksamensprojekt.model;

public class Bruger {

    private String id, brugerNavn, billedeURL;

    public Bruger(String id, String brugerNavn, String billedeURL) {
        this.id = id;
        this.brugerNavn = brugerNavn;
        this.billedeURL = billedeURL;
    }

    public Bruger() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrugerNavn() {
        return brugerNavn;
    }

    public void setBrugerNavn(String brugerNavn) {
        this.brugerNavn = brugerNavn;
    }

    public String getBilledeURL() {
        return billedeURL;
    }

    public void setBilledeURL(String billedeURL) {
        this.billedeURL = billedeURL;
    }
}
