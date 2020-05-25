package com.example.eksamensprojekt.data.model;

public class Bruger {

    private String id, fornavn, billedeURL;

    public Bruger(String id, String fornavn, String billedeURL) {
        this.id = id;
        this.fornavn = fornavn;
        this.billedeURL = billedeURL;
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
}
