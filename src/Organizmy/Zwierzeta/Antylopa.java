import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antylopa extends Zwierze {
    public Antylopa(Punkt polozenie) {
        super(polozenie, 4, 4, "🦌");
    }

    @Override
    public String nazwa() {
        return "Antylopa";
    }

    @Override
    public void akcja() {
        List<Punkt> sasiednie = SwiatGlobalny.getSasiedniePola(polozenie);
        List<Punkt> dalsze = new ArrayList<>();

        for (Punkt p : sasiednie) {
            int dx = p.getX() - polozenie.getX();
            int dy = p.getY() - polozenie.getY();
            Punkt dalej = new Punkt(p.getX() + dx, p.getY() + dy);

            if (SwiatGlobalny.isWPlanszy(dalej)) {
                dalsze.add(dalej);
            }
        }

        if (!dalsze.isEmpty()) {
            Punkt cel = dalsze.get(new Random().nextInt(dalsze.size()));
            Organizm o = SwiatGlobalny.getOrganizmNa(cel);

            if (o != null) {
                kolizja(o);
            } else {
                setPolozenie(cel);
            }
        }

        zwiekszWiek();
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny == null) return;

        if (new Random().nextBoolean()) {
            List<Punkt> wolne = SwiatGlobalny.getWolnePolaObok(polozenie);
            if (!wolne.isEmpty()) {
                Punkt ucieczka = wolne.get(new Random().nextInt(wolne.size()));
                setPolozenie(ucieczka);
                SwiatGlobalny.dodajLog(nazwa() + " uciekła przed " + inny.nazwa());
                return;
            }
        }

        super.kolizja(inny);
    }
}
