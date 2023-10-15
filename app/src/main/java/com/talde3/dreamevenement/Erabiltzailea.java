package com.talde3.dreamevenement;

public class Erabiltzailea {
    private String email;
    private String pasahitza;
    private String nan;
    private String izena;
    private String telefonoa;
    private String nif;
    private Boolean enpresa;

    // COnstructors


    public Erabiltzailea(String email, String pasahitza, String nan, String izena, String telefonoa, String nif, Boolean enpresa) {
        this.email = email;
        this.pasahitza = pasahitza;
        this.nan = nan;
        this.izena = izena;
        this.telefonoa = telefonoa;
        this.nif = nif;
        this.enpresa = enpresa;
    }

    public Erabiltzailea() {
    }

    // Getters
    public String getEmail() {
        return email;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public String getNan() {
        return nan;
    }
    public String getIzena() {
        return izena;
    }
    public String getTelefonoa() {
        return telefonoa;
    }
    public String getNif() {
        return nif;
    }
    public Boolean getEnpresa() {
        return enpresa;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }
    public void setNan(String nan) {
        this.nan = nan;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }
    public void setTelefonoa(String telefonoa) {
        this.telefonoa = telefonoa;
    }
    public void setNif(String nif) {
        this.nif = nif;
    }
    public void setEnpresa(Boolean enpresa) {
        this.enpresa = enpresa;
    }
}
