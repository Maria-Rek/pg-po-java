package Organizmy;

import Utils.Punkt;
import java.awt.Image;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int wiek;
    protected Punkt polozenie;

    public Organizm(Punkt polozenie, int sila, int inicjatywa) {
        this.polozenie = polozenie;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.wiek = 0;
    }

    public abstract void akcja();
    public abstract void kolizja(Organizm inny);
    public abstract String nazwa();
    public abstract Image getObrazek();

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

    public void setWiek(int wiek) {  // ⬅️ 🔧 DODANE
        this.wiek = wiek;
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

    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false; // Domyślnie nikt nie odbija
    }

    public boolean czyZwierze() {
        return this instanceof Zwierze;
    }

    public boolean czyRoslina() {
        return this instanceof Roslina;
    }

    public boolean tenSamGatunek(Organizm inny) {
        return this.getClass().equals(inny.getClass());
    }
}
