package Organizmy;

import Swiat.SwiatGlobalny;
import Utils.Punkt;

import java.util.List;
import java.util.Random;

public abstract class Zwierze extends Organizm {
    public Zwierze(Punkt polozenie, int sila, int inicjatywa) {
        super(polozenie, sila, inicjatywa);
    }

    @Override
    public void akcja() {
        List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(polozenie);
        if (!sasiednie.isEmpty()) {
            Punkt nowaPozycja = sasiednie.get(new Random().nextInt(sasiednie.size()));
            Organizm cel = SwiatGlobalny.getOrganizmNa(nowaPozycja);

            if (cel != null) {
                kolizja(cel);
            } else {
                setPolozenie(nowaPozycja);
            }
        }

        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if (inny instanceof Roslina) { //roslina obsługuje kolizje
            inny.kolizja(this);
            return;
        }

        if (inny.czyOdbilAtak(this)) {
            SwiatGlobalny.dodajLog(inny.nazwa() + " odbił atak " + this.nazwa());
            return;
        }

        if (tenSamGatunek(inny) && this != inny) {
            List<Punkt> wolne = SwiatGlobalny.getWolnePolaObok(polozenie);
            if (!wolne.isEmpty()) {
                Punkt dzieckoPozycja = wolne.get(new Random().nextInt(wolne.size()));
                SwiatGlobalny.stworzOrganizm(this.getClass(), dzieckoPozycja);
                SwiatGlobalny.dodajLog(nazwa() + " rozmnożył się");
            }
            return;
        }

        if (this.sila >= inny.getSila()) {
            SwiatGlobalny.dodajLog(inny.nazwa() + " został zabity przez " + this.nazwa());
            SwiatGlobalny.usunOrganizm(inny);
            this.setPolozenie(inny.getPolozenie());
        } else {
            SwiatGlobalny.dodajLog(this.nazwa() + " został zabity przez " + inny.nazwa());
            SwiatGlobalny.usunOrganizm(this);
        }
    }
}
