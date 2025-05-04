package Organizmy.Zwierzeta;

import Utils.Punkt;
import Organizmy.Zwierze;
import Organizmy.Organizm;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Punkt polozenie) {
        super(polozenie, 4, 4);
    }

    @Override
    public String nazwa() {
        return "Owca";
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if ("Trawa".equals(inny.nazwa())) {
            SwiatGlobalny.dodajLog("Owca zjadła Trawę – bez efektu.");
            SwiatGlobalny.usunOrganizm(inny);  // Trawa znika
            setPolozenie(inny.getPolozenie());
            return;
        }

        // domyślna kolizja
        super.kolizja(inny);
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/owca.png")).getImage();
    }
}
