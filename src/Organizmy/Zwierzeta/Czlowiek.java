package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Organizmy.Organizm;
import Utils.Punkt;
import Utils.Kierunek;
import Swiat.SwiatGlobalny;

import java.util.List;

public class Czlowiek extends Zwierze {
    private boolean specjalnaAktywna = false;
    private int turyAktywne = 0;
    private int turyCooldown = 0;
    private Kierunek zaplanowanyRuch = Kierunek.BRAK;

    public Czlowiek(Punkt polozenie) {
        super(polozenie, 5, 4, "🚹");
    }

    @Override
    public String nazwa() {
        return "Czlowiek";
    }

    public void zaplanujRuch(Kierunek kierunek) {
        this.zaplanowanyRuch = kierunek;
    }

    public void aktywujSpecjalna() {
        if (!specjalnaAktywna && turyCooldown == 0) {
            specjalnaAktywna = true;
            turyAktywne = 5;
            SwiatGlobalny.dodajLog("Człowiek użył umiejętności: całopalenie 🔥");
        }
    }

    @Override
    public void akcja() {
        if (specjalnaAktywna) {
            List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(getPolozenie());
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
                SwiatGlobalny.dodajLog("Umiejętność Człowieka wygasła. Rozpoczęto 5-tur cooldown.");
            }
        } else if (turyCooldown > 0) {
            turyCooldown--;
        }

        if (zaplanowanyRuch != Kierunek.BRAK) {
            Punkt cel = zaplanowanyRuch.krok(getPolozenie());

            // Sprawdź ręcznie czy w planszy
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
        zwiekszWiek();
    }

    public int getCooldown() {
        return turyCooldown;
    }

    public int getTuryAktywne() {
        return turyAktywne;
    }

    public boolean isSpecjalnaAktywna() {
        return specjalnaAktywna;
    }
}
