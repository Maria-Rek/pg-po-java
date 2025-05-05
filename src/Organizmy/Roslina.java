package Organizmy;

import Swiat.SwiatGlobalny;
import Utils.Punkt;

import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {
    public Roslina(Punkt polozenie, int sila) {
        super(polozenie, sila, 0);
    }

    @Override
    public void akcja() {
        if (new Random().nextInt(10) == 0) {
            List<Punkt> wolne = SwiatGlobalny.getWolnePolaObok(polozenie);
            if (!wolne.isEmpty()) {
                Punkt nowaPozycja = wolne.get(new Random().nextInt(wolne.size()));
                SwiatGlobalny.stworzOrganizm(this.getClass(), nowaPozycja);
                SwiatGlobalny.dodajLog(nazwa() + " rozsiało się");
            }
        }
        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        Punkt moje = this.getPolozenie();

        if (this instanceof Organizmy.Rosliny.Trawa) {
            if (inny.getClass().getSimpleName().equals("Owca")) {
                SwiatGlobalny.dodajLog("Trawa została zjedzona przez Owcę");
                SwiatGlobalny.usunOrganizm(this);
            } else {
                SwiatGlobalny.dodajLog(inny.nazwa() + " stanęło na Trawie");
                inny.setPolozenie(moje);
            }
            return;
        }

        if (this instanceof Organizmy.Rosliny.Mlecz) {
            SwiatGlobalny.dodajLog(inny.nazwa() + " stanęło na Mleczu");
            inny.setPolozenie(moje);
            return;
        }

        SwiatGlobalny.dodajLog(nazwa() + " zostało zjedzone przez " + inny.nazwa());
        SwiatGlobalny.usunOrganizm(this);
        inny.setPolozenie(moje);
    }
}
