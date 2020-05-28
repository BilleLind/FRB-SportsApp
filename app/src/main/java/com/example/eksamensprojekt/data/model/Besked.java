package com.example.eksamensprojekt.data.model;

public class Besked {

    private String afsender;
    private String modtager;
    private String besked;

    public Besked(String afsender, String modtager, String besked) {
        this.afsender = afsender;
        this.modtager = modtager;
        this.besked = besked;
    }

    public Besked() {
    }

    public String getAfsender() {
        return afsender;
    }

    public void setAfsender(String afsender) {
        this.afsender = afsender;
    }

    public String getModtager() {
        return modtager;
    }

    public void setModtager(String modtager) {
        this.modtager = modtager;
    }

    public String getBesked() {
        return besked;
    }

    public void setBesked(String besked) {
        this.besked = besked;
    }
}
