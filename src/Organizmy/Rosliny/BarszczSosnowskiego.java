package Organizmy.Rosliny;

import Organizmy.Organizm;
import Organizmy.Roslina;
import Organizmy.Zwierzeta.CyberOwca;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarszczSosnowskiego extends Roslina {
    public BarszczSosnowskiego(Punkt polozenie) {
        super(polozenie, 10);
    }

    @Override
    public String nazwa() {
        return "BarszczSosnowskiego";
    }

    @Override
    public void akcja() {
        List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(getPolozenie());

        for (Punkt p : sasiednie) {
            Organizm o = SwiatGlobalny.getOrganizmNa(p);
            if (o != null && !(o instanceof CyberOwca) && !(o instanceof Roslina)) {
                SwiatGlobalny.dodajLog(nazwa() + " zabił " + o.nazwa() + " obok siebie");
                SwiatGlobalny.usunOrganizm(o);
            }
        }

        super.akcja();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny instanceof CyberOwca) {
            SwiatGlobalny.dodajLog(inny.nazwa() + " zjadł " + nazwa() + " i przetrwał!");
        } else {
            SwiatGlobalny.dodajLog(inny.nazwa() + " zjadł " + nazwa() + " i zginął!");
            SwiatGlobalny.usunOrganizm(inny);
        }
        SwiatGlobalny.usunOrganizm(this);
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/barszcz.png")).getImage();
    }
}
