package Organizmy.Zwierzeta;

import Utils.Punkt;
import Organizmy.Zwierze;

public class Owca extends Zwierze {
    public Owca(Punkt polozenie) {
        super(polozenie, 4, 4, "🐑");
    }

    @Override
    public String nazwa() {
        return "Owca";
    }
}
