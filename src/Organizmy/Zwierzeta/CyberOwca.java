package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Organizmy.Organizm;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CyberOwca extends Zwierze {
    public CyberOwca(Punkt polozenie) {
        super(polozenie, 11, 4);
    }

    @Override
    public String nazwa() {
        return "CyberOwca";
    }

    @Override
    public void akcja() {
        Organizm najblizszyBarszcz = znajdzNajblizszyBarszcz();

        if (najblizszyBarszcz != null) {
            Punkt cel = przesunWStrone(najblizszyBarszcz.getPolozenie());
            Organizm o = SwiatGlobalny.getOrganizmNa(cel);

            if (o != null) {
                kolizja(o);
            } else {
                setPolozenie(cel);
            }
        } else {
            SwiatGlobalny.dodajLog(nazwa() + " rozglądała się, ale nie znalazła Barszczu Sosnowskiego.");
        }

        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if (inny.nazwa().equals("BarszczSosnowskiego")) {
            SwiatGlobalny.dodajLog(inny.nazwa() + " został zabity przez CyberOwcę");
            SwiatGlobalny.usunOrganizm(inny);
            setPolozenie(inny.getPolozenie());
            return;
        }

        super.kolizja(inny);
    }

    private Organizm znajdzNajblizszyBarszcz() {
        List<Organizm> lista = SwiatGlobalny.pobierzSwiat().getOrganizmy();
        Organizm najblizszy = null;
        int minDystans = Integer.MAX_VALUE;

        for (Organizm o : lista) {
            if ("BarszczSosnowskiego".equals(o.nazwa())) {
                int dystans = Math.abs(o.getPolozenie().getX() - getPolozenie().getX()) +
                        Math.abs(o.getPolozenie().getY() - getPolozenie().getY());
                if (dystans < minDystans) {
                    minDystans = dystans;
                    najblizszy = o;
                }
            }
        }

        return najblizszy;
    }

    private Punkt przesunWStrone(Punkt cel) {
        int dx = Integer.compare(cel.getX(), getPolozenie().getX());
        int dy = Integer.compare(cel.getY(), getPolozenie().getY());
        return new Punkt(getPolozenie().getX() + dx, getPolozenie().getY() + dy);
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/cyber.png")).getImage();
    }
}
