package Organizmy.Rosliny;

import Organizmy.Organizm;
import Organizmy.Roslina;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;

public class WilczeJagody extends Roslina {
    public WilczeJagody(Punkt polozenie) {
        super(polozenie, 99);
    }

    @Override
    public String nazwa() {
        return "WilczeJagody";
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        SwiatGlobalny.dodajLog(inny.nazwa() + " zjadł " + nazwa() + " i zginął!");
        SwiatGlobalny.usunOrganizm(inny);
        SwiatGlobalny.usunOrganizm(this);
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/jagody.png")).getImage();
    }
}
