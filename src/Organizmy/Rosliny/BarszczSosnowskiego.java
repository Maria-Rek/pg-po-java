package Organizmy.Rosliny;

import Organizmy.Organizm;
import Organizmy.Roslina;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import java.util.List;

public class BarszczSosnowskiego extends Roslina {
    public BarszczSosnowskiego(Punkt polozenie) {
        super(polozenie, 10, "🧪");
    }

    @Override
    public String nazwa() {
        return "Barszcz Sosnowskiego";
    }

    @Override
    public void akcja() {
        List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(getPolozenie());

        for (Punkt p : sasiednie) {
            Organizm o = SwiatGlobalny.getOrganizmNa(p);
            if (o != null && !("CyberOwca".equals(o.nazwa()))) {
                SwiatGlobalny.dodajLog(nazwa() + " zabił " + o.nazwa() + " obok siebie");
                SwiatGlobalny.usunOrganizm(o);
            }
        }

        super.akcja();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny != null) {
            if ("CyberOwca".equals(inny.nazwa())) {
                SwiatGlobalny.dodajLog(inny.nazwa() + " zjadł " + nazwa() + " i przetrwał!");
                SwiatGlobalny.usunOrganizm(this);
            } else {
                SwiatGlobalny.dodajLog(inny.nazwa() + " zjadł " + nazwa() + " i zginął!");
                SwiatGlobalny.usunOrganizm(inny);
                SwiatGlobalny.usunOrganizm(this);
            }
        }
    }
}
