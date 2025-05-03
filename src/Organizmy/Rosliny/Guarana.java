package Organizmy.Rosliny;

import Organizmy.Roslina;
import Organizmy.Organizm;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

public class Guarana extends Roslina {
    public Guarana(Punkt polozenie) {
        super(polozenie, 0, "🍒");
    }

    @Override
    public String nazwa() {
        return "Guarana";
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny != null) {
            inny.setSila(inny.getSila() + 3);
            inny.setPolozenie(getPolozenie());
            SwiatGlobalny.dodajLog(nazwa() + " została zjedzona przez " + inny.nazwa() + " (zyskał +3 siły)");
            SwiatGlobalny.usunOrganizm(this);
        }
    }
}
