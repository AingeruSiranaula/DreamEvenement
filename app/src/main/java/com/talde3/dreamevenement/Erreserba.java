package com.talde3.dreamevenement;

import java.io.Serializable;

public class Erreserba implements Serializable {
    private int argazki_ordu;
    private int gonbidatuak;
    private double alokairua_tarifa;
    private double apainketa_tarifa;
    private double argazkilaria_tarifa;
    private double menua_tarifa;
    private double totala;

    // Constructor
    public Erreserba(int argazki_ordu, int gonbidatuak, double alokairua_tarifa, double apainketa_tarifa, double argazkilaria_tarifa, double menua_tarifa, double totala) {
        this.argazki_ordu = argazki_ordu;
        this.gonbidatuak = gonbidatuak;
        this.alokairua_tarifa = alokairua_tarifa;
        this.apainketa_tarifa = apainketa_tarifa;
        this.argazkilaria_tarifa = argazkilaria_tarifa;
        this.menua_tarifa = menua_tarifa;
        this.totala = totala;
    }

    public Erreserba() {}

    // Getters
    public int getArgazki_ordu() {
        return argazki_ordu;
    }
    public int getGonbidatuak() {
        return gonbidatuak;
    }
    public double getAlokairua_tarifa() {
        return alokairua_tarifa;
    }
    public double getApainketa_tarifa() {
        return apainketa_tarifa;
    }
    public double getArgazkilaria_tarifa() {
        return argazkilaria_tarifa;
    }
    public double getMenua_tarifa() {
        return menua_tarifa;
    }
    public double getTotala() {
        return totala;
    }

    // Setters
    public void setArgazki_ordu(int argazki_ordu) {
        this.argazki_ordu = argazki_ordu;
    }
    public void setGonbidatuak(int gonbidatuak) {
        this.gonbidatuak = gonbidatuak;
    }
    public void setAlokairua_tarifa(double alokairua_tarifa) {
        this.alokairua_tarifa = alokairua_tarifa;
    }
    public void setApainketa_tarifa(double apainketa_tarifa) {
        this.apainketa_tarifa = apainketa_tarifa;
    }
    public void setArgazkilaria_tarifa(double argazkilaria_tarifa) {
        this.argazkilaria_tarifa = argazkilaria_tarifa;
    }
    public void setMenua_tarifa(double menua_tarifa) {
        this.menua_tarifa = menua_tarifa;
    }
    public void setTotala(double totala) {
        this.totala = totala;
    }
}
