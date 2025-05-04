package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Organizmy.Organizm;
import Swiat.SwiatGlobalny;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Lis extends Zwierze {
    public Lis(Punkt polozenie) {
        super(polozenie, 3, 7);
    }

    @Override
    public String nazwa() {
        return "Lis";
    }

    @Override
    public void akcja() {
        List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(getPolozenie());
        List<Punkt> bezpieczne = new ArrayList<>();

        for (Punkt p : sasiednie) {
            Organizm o = SwiatGlobalny.getOrganizmNa(p);
            if (o == null || o.getSila() <= this.getSila()) {
                bezpieczne.add(p);
            }
        }

        if (!bezpieczne.isEmpty()) {
            Punkt nowaPozycja = bezpieczne.get(new Random().nextInt(bezpieczne.size()));
            Organizm cel = SwiatGlobalny.getOrganizmNa(nowaPozycja);

            if (cel != null) {
                kolizja(cel);
            } else {
                setPolozenie(nowaPozycja);
            }
        } else {
            SwiatGlobalny.dodajLog(nazwa() + " nie znalazł bezpiecznego pola i pozostał w miejscu");
        }

        zwiekszWiek();
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/lis.png")).getImage();
    }
}
