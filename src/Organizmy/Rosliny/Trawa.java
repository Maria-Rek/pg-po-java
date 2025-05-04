package Organizmy.Rosliny;

import Organizmy.Organizm;
import Organizmy.Roslina;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(Punkt polozenie) {
        super(polozenie, 0);
    }

    @Override
    public String nazwa() {
        return "Trawa";
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if ("Owca".equals(inny.nazwa())) {
            SwiatGlobalny.dodajLog(nazwa() + " została zjedzona przez " + inny.nazwa());
            SwiatGlobalny.usunOrganizm(this);
        } else {
            SwiatGlobalny.dodajLog(inny.nazwa() + " wszedł na " + nazwa() + ", ale jej nie zjadł.");
            // Trawa zostaje na planszy
        }
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/trawa.png")).getImage();
    }
}
