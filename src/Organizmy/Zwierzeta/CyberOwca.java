import java.util.List;

public class CyberOwca extends Zwierze {
    public CyberOwca(Punkt polozenie) {
        super(polozenie, 11, 4, "🤖");
    }

    @Override
    public String nazwa() {
        return "CyberOwca";
    }

    @Override
    public void akcja() {
        Punkt cel = znajdzNajblizszyBarszcz();

        if (cel == null) {
            SwiatGlobalny.dodajLog(nazwa() + " rozglądała się, ale nie znalazła Barszczu Sosnowskiego.");
            zwiekszWiek();
            return;
        }

        int dx = Integer.compare(cel.getX(), polozenie.getX());
        int dy = Integer.compare(cel.getY(), polozenie.getY());

        Punkt nowaPozycja = new Punkt(polozenie.getX() + dx, polozenie.getY() + dy);

        if (!nowaPozycja.equals(polozenie)) {
            Organizm celny = SwiatGlobalny.getOrganizmNa(nowaPozycja);
            if (celny != null) {
                kolizja(celny);
            } else {
                setPolozenie(nowaPozycja);
            }
        }

        zwiekszWiek();
    }

    private Punkt znajdzNajblizszyBarszcz() {
        List<Organizm> organizmy = SwiatGlobalny.getWszystkieOrganizmy();
        Punkt najblizszy = null;
        int minDystans = Integer.MAX_VALUE;

        for (Organizm o : organizmy) {
            if ("Barszcz Sosnowskiego".equals(o.nazwa())) {
                int d = Math.abs(polozenie.getX() - o.getPolozenie().getX()) +
                        Math.abs(polozenie.getY() - o.getPolozenie().getY());
                if (d < minDystans) {
                    minDystans = d;
                    najblizszy = o.getPolozenie();
                }
            }
        }

        return najblizszy;
    }
}
