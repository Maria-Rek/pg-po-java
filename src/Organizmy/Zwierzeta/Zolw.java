package Organizmy.Zwierzeta;

import Organizmy.Organizm;
import Organizmy.Zwierze;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze {
    public Zolw(Punkt polozenie) {
        super(polozenie, 2, 1);
    }

    @Override
    public String nazwa() {
        return "Zolw";
    }

    @Override
    public void akcja() {
        if (new Random().nextInt(4) == 0) { // 25% szansy na ruch
            super.akcja();
        } else {
            SwiatGlobalny.dodajLog(nazwa() + " nie poruszył się (25% szansy na ruch)");
            zwiekszWiek();
        }
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        // Żółw nie niszczy Trawy ani Mlecza – tylko przez nie przechodzi
        if ("Trawa".equals(inny.nazwa()) || "Mlecz".equals(inny.nazwa())) {
            setPolozenie(inny.getPolozenie());
            SwiatGlobalny.dodajLog(nazwa() + " przeszedł przez " + inny.nazwa() + ".");
            return;
        }

        super.kolizja(inny);
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return atakujacy.getSila() < 5;
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/zolw.png")).getImage();
    }
}
