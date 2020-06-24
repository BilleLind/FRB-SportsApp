package com.example.eksamensprojekt.presentation.Interface;

public interface Konstante {
    // en klasse hvor man kan initialisere variabler som man bruger meget

    String brugere = "Brugere"; //bruges i forbindelse med database referencer for brugere. fornavn, efternavn brugerid gemmes under "Brugere"
    String chats = "Chats"; //bruges i forbindelse med database referencer for beskeder (den afdeleling beskeder samt afsender og modtager brugerid gemmes)
    String brugerId = "brugerid"; // bruges for at adskille de forskellige brugere fra hinanden samt for at kontakte hinanden og gemme data i firebase
    int VEALG_BILLEDE_ANMODNING = 1;
}
