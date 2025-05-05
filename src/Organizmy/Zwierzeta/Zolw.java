package Organizmy.Zwierzeta;

import Organizmy.Organizm;
import Organizmy.Zwierze;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;

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
        if (Math.random() < 0.25) { // 25% szansy na ruch
            super.akcja();
        } else {
            SwiatGlobalny.dodajLog(nazwa() + " nie poruszył się (25% szansy na ruch)");
            zwiekszWiek();
        }
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
