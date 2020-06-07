package com.example.eksamensprojekt.data.model;

public class Oovelser {

    private String name;
    private String videoURL;
    private String Image;

    public Oovelser(String name, String videoURL, String Image) {
        this.name = name;
        this.videoURL = videoURL;
        this.Image = Image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
