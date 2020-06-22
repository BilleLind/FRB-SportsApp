package com.example.eksamensprojekt.data.model;

public class SamtaleListe {

    private String brugerid;

    public SamtaleListe(String brugerid) {
        this.brugerid = brugerid;
    }

    public SamtaleListe() {
    }

    public String getBrugerid() {
        return brugerid;
    }

    public void setBrugerid(String brugerid) {
        this.brugerid = brugerid;
    }


}
