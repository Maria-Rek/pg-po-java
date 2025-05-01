import java.util.List;
import java.util.Random;

public class Zolw extends Zwierze {
    public Zolw(Punkt polozenie) {
        super(polozenie, 2, 1, "🐢");
    }

    @Override
    public String nazwa() {
        return "Zolw";
    }

    @Override
    public void akcja() {
        if (new Random().nextInt(4) == 0) { // 25% szansy
            super.akcja();
        } else {
            SwiatGlobalny.dodajLog(nazwa() + " nie poruszył się (25% szansy na ruch)");
            zwiekszWiek();
        }
    }

    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return atakujacy.getSila() < 5;
    }
}
