package com.talde3.dreamevenement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Eskontza implements Serializable {
    private String deskribapena;
    private ArrayList<Number> gonbidatuak;
    private int id;
    private String izena;
    private ArrayList<Number> prezioak;
    private String argazkia;

    // Constructor
    public Eskontza(String deskribapena, ArrayList<Number> gonbidatuak, int id, String izena, ArrayList<Number> prezioak, String argazkia) {
        this.deskribapena = deskribapena;
        this.gonbidatuak = gonbidatuak;
        this.id = id;
        this.izena = izena;
        this.prezioak = prezioak;
        this.argazkia= argazkia;
    }

    public Eskontza() {
    }

    // Getters
    public String getDeskribapena() {
        return deskribapena;
    }

    public ArrayList<Number> getGonbidatuak() {
        return gonbidatuak;
    }

    public int getId() {
        return id;
    }

    public String getIzena() {
        return izena;
    }

    public ArrayList<Number> getPrezioak() {
        return prezioak;
    }

    public String getArgazkia (){ return argazkia;}

    // Settters
    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }

    public void setGonbidatuak(ArrayList<Number> gonbidatuak) {
        this.gonbidatuak = gonbidatuak;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public void setPrezioak(ArrayList<Number> prezioak) {
        this.prezioak = prezioak;
    }

    public void setArgazkia (String argazkia){this.argazkia=argazkia;}
}
