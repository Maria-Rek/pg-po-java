package Swiat;

import Organizmy.Organizm;
import Utils.Punkt;

import java.util.ArrayList;
import java.util.List;

public class SwiatKwadratowy implements ISwiat {
    private final int szerokosc;
    private final int wysokosc;
    private final List<Organizm> organizmy = new ArrayList<>();
    private final List<String> logi = new ArrayList<>();

    public SwiatKwadratowy(int szerokosc, int wysokosc) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
    }

    @Override
    public int getSzerokosc() {
        return szerokosc;
    }

    @Override
    public int getWysokosc() {
        return wysokosc;
    }

    @Override
    public boolean czyPoleZajete(Punkt p) {
        return znajdzOrganizm(p) != null;
    }

    @Override
    public Organizm znajdzOrganizm(Punkt p) {
        for (Organizm o : organizmy) {
            if (o.getPolozenie().equals(p)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void dodajOrganizm(Organizm o) {
        organizmy.add(o);
    }

    @Override
    public void usunOrganizm(Organizm o) {
        organizmy.remove(o);
    }

    @Override
    public void dodajLog(String log) {
        logi.add(log);
    }

    @Override
    public void wypiszLogi() {
        for (String log : logi) {
            System.out.println(log);
        }
        logi.clear();
    }

    @Override
    public List<Organizm> getOrganizmy() {
        return organizmy;
    }
}
