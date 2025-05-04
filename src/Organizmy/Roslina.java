package Organizmy;

import Swiat.SwiatGlobalny;
import Utils.Punkt;

import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {
    public Roslina(Punkt polozenie, int sila) {
        super(polozenie, sila, 0); // Rośliny mają inicjatywę 0
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

        // Trawa: zjadana tylko przez Owcę
        if (this.getClass().getSimpleName().equals("Trawa")) {
            if (inny.getClass().getSimpleName().equals("Owca")) {
                SwiatGlobalny.dodajLog("Trawa została zjedzona przez Owcę (bez efektu)");
                SwiatGlobalny.usunOrganizm(this);
            } else {
                SwiatGlobalny.dodajLog(inny.nazwa() + " stanęło na Trawie – brak reakcji");
            }
            return;
        }

        // Mlecz – nie jest zjadany przez nikogo
        if (this.getClass().getSimpleName().equals("Mlecz")) {
            SwiatGlobalny.dodajLog(inny.nazwa() + " stanęło na Mleczu – brak reakcji");
            return;
        }

        // Inne rośliny mogą być zjadane
        SwiatGlobalny.dodajLog(nazwa() + " zostało zjedzone przez " + inny.nazwa());
        SwiatGlobalny.usunOrganizm(this);
    }
}
