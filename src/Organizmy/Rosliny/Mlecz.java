package Organizmy.Rosliny;

import Organizmy.Roslina;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import java.util.List;
import java.util.Random;

public class Mlecz extends Roslina {
    public Mlecz(Punkt polozenie) {
        super(polozenie, 0, "🌼");
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
}
