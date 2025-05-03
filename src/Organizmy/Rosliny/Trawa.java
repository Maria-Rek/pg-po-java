package Organizmy.Rosliny;

import Organizmy.Roslina;
import Utils.Punkt;

public class Trawa extends Roslina {
    public Trawa(Punkt polozenie) {
        super(polozenie, 0, "🌿");
    }

    @Override
    public String nazwa() {
        return "Trawa";
    }
}
