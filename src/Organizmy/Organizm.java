package Organizmy;

import Utils.Punkt;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int wiek;
    protected Punkt polozenie;
    protected String ikona;

    public Organizm(Punkt polozenie, int sila, int inicjatywa, String ikona) {
        this.polozenie = polozenie;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.wiek = 0;
        this.ikona = ikona;
    }

    public abstract void akcja();
    public abstract void kolizja(Organizm inny);
    public abstract String nazwa();

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getWiek() {
        return wiek;
    }

    public void zwiekszWiek() {
        this.wiek++;
    }

    public Punkt getPolozenie() {
        return polozenie;
    }

    public void setPolozenie(Punkt polozenie) {
        this.polozenie = polozenie;
    }

    public String getIkona() {
        return ikona;
    }

    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }
}
