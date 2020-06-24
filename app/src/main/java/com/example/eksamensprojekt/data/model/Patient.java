package com.example.eksamensprojekt.data.model;

import java.util.List;

public class Patient extends Bruger{

    private List<String> skade;

    public List<String> getSkade() {
        return skade;
    }

    public void setSkade(List<String> skade) {
        this.skade = skade;
    }
}
