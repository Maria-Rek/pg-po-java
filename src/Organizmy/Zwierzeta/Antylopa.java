package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Organizmy.Organizm;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antylopa extends Zwierze {
    public Antylopa(Punkt polozenie) {
        super(polozenie, 4, 4);
    }

    @Override
    public String nazwa() {
        return "Antylopa";
    }

    @Override
    public void akcja() {
        List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(getPolozenie());
        List<Punkt> dalsze = new ArrayList<>();

        for (Punkt p : sasiednie) {
            int dx = p.getX() - getPolozenie().getX();
            int dy = p.getY() - getPolozenie().getY();
            Punkt dalej = new Punkt(p.getX() + dx, p.getY() + dy);

            if (dalej.getX() >= 0 && dalej.getX() < SwiatGlobalny.pobierzSwiat().getSzerokosc() &&
                    dalej.getY() >= 0 && dalej.getY() < SwiatGlobalny.pobierzSwiat().getWysokosc()) {
                dalsze.add(dalej);
            }
        }

        if (!dalsze.isEmpty()) {
            Punkt cel = dalsze.get(new Random().nextInt(dalsze.size()));
            Organizm o = SwiatGlobalny.getOrganizmNa(cel);

            if (o != null) {
                kolizja(o);
            } else {
                setPolozenie(cel);
            }
        }

        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if (new Random().nextBoolean()) {
            List<Punkt> wolne = SwiatGlobalny.getWolnePolaObok(getPolozenie());
            if (!wolne.isEmpty()) {
                Punkt ucieczka = wolne.get(new Random().nextInt(wolne.size()));
                setPolozenie(ucieczka);
                SwiatGlobalny.dodajLog(nazwa() + " uciekła przed " + inny.nazwa());
                return;
            } else {
                SwiatGlobalny.dodajLog(nazwa() + " chciała uciec, ale nie miała gdzie.");
            }
        }

        super.kolizja(inny);
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/antylopa.png")).getImage();
    }
}
