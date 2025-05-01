import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {
    public Roslina(Punkt polozenie, int sila, String ikona) {
        super(polozenie, sila, 0, ikona); // inicjatywa = 0
    }

    @Override
    public void akcja() {
        if (new Random().nextInt(10) == 0) { // 10% szans
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

        SwiatGlobalny.dodajLog(nazwa() + " zostało zjedzone przez " + inny.nazwa());
        SwiatGlobalny.usunOrganizm(this);
    }
}
