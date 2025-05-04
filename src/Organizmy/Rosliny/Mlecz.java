package Organizmy.Rosliny;

import Organizmy.Organizm;
import Organizmy.Roslina;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Mlecz extends Roslina {
    public Mlecz(Punkt polozenie) {
        super(polozenie, 0);
    }

    @Override
    public String nazwa() {
        return "Mlecz";
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 3; i++) {
            if (new Random().nextInt(10) == 0) {
                List<Punkt> wolne = SwiatGlobalny.getWolnePolaObok(getPolozenie());
                if (!wolne.isEmpty()) {
                    Punkt nowaPozycja = wolne.get(new Random().nextInt(wolne.size()));
                    SwiatGlobalny.stworzOrganizm(Mlecz.class, nowaPozycja);
                    SwiatGlobalny.dodajLog(nazwa() + " rozsiało się");
                }
            }
        }
        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if ("Owca".equals(inny.nazwa())) {
            SwiatGlobalny.dodajLog(nazwa() + " został zjedzony przez " + inny.nazwa());
            SwiatGlobalny.usunOrganizm(this);
        } else {
            SwiatGlobalny.dodajLog(inny.nazwa() + " wszedł na " + nazwa() + ", ale jej nie zjadł.");
        }
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/mlecz.png")).getImage();
    }
}
