package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Organizmy.Organizm;
import Swiat.SwiatGlobalny;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;

public class Wilk extends Zwierze {
    public Wilk(Punkt polozenie) {
        super(polozenie, 9, 5);
    }

    @Override
    public String nazwa() {
        return "Wilk";
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        // Wilk nie niszczy Trawy ani Mlecza – tylko przechodzi
        if ("Trawa".equals(inny.nazwa()) || "Mlecz".equals(inny.nazwa())) {
            setPolozenie(inny.getPolozenie());
            SwiatGlobalny.dodajLog(nazwa() + " przeszedł przez " + inny.nazwa() + ".");
            return;
        }

        super.kolizja(inny);
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/wilk.png")).getImage();
    }
}
