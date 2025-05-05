package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Organizmy.Organizm;
import Utils.Punkt;
import Utils.Kierunek;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Czlowiek extends Zwierze {
    private boolean specjalnaAktywna = false;
    private int turyAktywne = 0;
    private int turyCooldown = 0;
    private boolean czekaNaStartSpecjalnej = false;
    private Kierunek zaplanowanyRuch = Kierunek.BRAK;

    public Czlowiek(Punkt polozenie) {
        super(polozenie, 5, 4);
    }

    @Override
    public String nazwa() {
        return "Czlowiek";
    }

    public void zaplanujRuch(Kierunek kierunek) {
        this.zaplanowanyRuch = kierunek;
    }

    public void aktywujSpecjalna() {
        if (!specjalnaAktywna && turyCooldown == 0 && !czekaNaStartSpecjalnej) {
            czekaNaStartSpecjalnej = true;
            SwiatGlobalny.dodajLog("Człowiek przygotowuje się do użycia całopalenia 🔥");
        }
    }

    @Override
    public void akcja() {
        // 1. Wykonaj ruch
        if (zaplanowanyRuch != Kierunek.BRAK) {
            Punkt cel = zaplanowanyRuch.krok(getPolozenie());
            if (cel.getX() >= 0 && cel.getX() < SwiatGlobalny.pobierzSwiat().getSzerokosc()
                    && cel.getY() >= 0 && cel.getY() < SwiatGlobalny.pobierzSwiat().getWysokosc()) {

                Organizm o = SwiatGlobalny.getOrganizmNa(cel);
                if (o != null) kolizja(o);
                else setPolozenie(cel);
            } else {
                SwiatGlobalny.dodajLog("Człowiek chciał wyjść poza mapę!");
            }
        }
        zaplanowanyRuch = Kierunek.BRAK;

        // 2. Aktywuj umiejętność, jeśli miała się aktywować
        if (czekaNaStartSpecjalnej) {
            specjalnaAktywna = true;
            turyAktywne = 5;
            czekaNaStartSpecjalnej = false;
            SwiatGlobalny.dodajLog("Człowiek aktywował całopalenie 🔥 (5 tur)");
        }

        // 3. Całopalenie z nowej pozycji
        if (specjalnaAktywna) {
            List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(getPolozenie(), true);
            for (Punkt p : sasiednie) {
                Organizm o = SwiatGlobalny.getOrganizmNa(p);
                if (o != null && o != this) {
                    SwiatGlobalny.dodajLog(o.nazwa() + " został spalony przez Człowieka 🔥");
                    SwiatGlobalny.usunOrganizm(o);
                }
            }

            turyAktywne--;
            if (turyAktywne == 0) {
                specjalnaAktywna = false;
                turyCooldown = 5;
                SwiatGlobalny.dodajLog("Umiejętność Człowieka wygasła. Cooldown 5 tur.");
            }
        } else if (turyCooldown > 0) {
            turyCooldown--;
        }

        // 4. Wiek
        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if (inny.nazwa().equals("BarszczSosnowskiego")) {
            SwiatGlobalny.dodajLog("Człowiek zginął po kontakcie z Barszczem Sosnowskiego.");
            SwiatGlobalny.usunOrganizm(this);
            return;
        }

        super.kolizja(inny);
    }

    public int getCooldown() {
        return turyCooldown;
    }

    public void setCooldown(int cooldown) {
        this.turyCooldown = cooldown;
    }

    public int getTuryAktywne() {
        return turyAktywne;
    }

    public void setTuryAktywne(int turyAktywne) {
        this.turyAktywne = turyAktywne;
    }

    public boolean isSpecjalnaAktywna() {
        return specjalnaAktywna;
    }

    public void setSpecjalnaAktywna(boolean aktywna) {
        this.specjalnaAktywna = aktywna;
    }

    public boolean isCzekaNaStartSpecjalnej() {
        return czekaNaStartSpecjalnej;
    }

    public void setCzekaNaStartSpecjalnej(boolean czeka) {
        this.czekaNaStartSpecjalnej = czeka;
    }

    public String getStatusTekst() {
        if (specjalnaAktywna) {
            return "Aktywna (" + turyAktywne + "/5) 🔥";
        } else if (czekaNaStartSpecjalnej) {
            return "Aktywuje się za chwilę ⏳";
        } else if (turyCooldown > 0) {
            return "Cooldown (" + turyCooldown + "/5)";
        } else {
            return "Gotowa";
        }
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/czlowiek.png")).getImage();
    }
}
