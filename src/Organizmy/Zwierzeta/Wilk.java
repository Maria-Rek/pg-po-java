package Organizmy.Zwierzeta;
import Organizmy.Zwierze;
import Utils.Punkt;


public class Wilk extends Zwierze {
    public Wilk(Punkt polozenie) {
        super(polozenie, 9, 5, "🐺");
    }

    @Override
    public String nazwa() {
        return "Wilk";
    }
}
