package com.example.eksamensprojekt.data.model;

import java.util.Map;

public class Besked {

    private String afsender;
    private String modtager;
    private String besked;
    private Long tid;
    private String billedeUrl;

    public Besked(String afsender, String modtager, String besked, Long tid) {
        this.afsender = afsender;
        this.modtager = modtager;
        this.besked = besked;
        this.tid = tid;
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

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getBilledeUrl() {
        return billedeUrl;
    }

    public void setBilledeUrl(String billedeUrl) {
        this.billedeUrl = billedeUrl;
    }
}
