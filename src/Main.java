import Organizmy.Zwierzeta.Owca;
import Organizmy.Rosliny.Mlecz;
import Organizmy.Zwierzeta.Czlowiek;
import Organizmy.Zwierzeta.Wilk;
import Swiat.SwiatKwadratowy;
import Swiat.SwiatGlobalny;
import Utils.Punkt;
import Organizmy.Organizm;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwiatKwadratowy swiat = new SwiatKwadratowy(10, 10);
        SwiatGlobalny.ustawSwiat(swiat);

        // Dodaj organizmy testowe
        swiat.dodajOrganizm(new Owca(new Punkt(2, 3)));
        swiat.dodajOrganizm(new Mlecz(new Punkt(5, 5)));
        swiat.dodajOrganizm(new Wilk(new Punkt(4, 5)));
        swiat.dodajOrganizm(new Czlowiek(new Punkt(4, 4)));

        swiat.dodajLog("Świat został zainicjalizowany.");

        // Symulacja 10 tur
        for (int tura = 1; tura <= 10; tura++) {
            swiat.dodajLog("=== Tura " + tura + " ===");

            List<Organizm> organizmySnapshot = new ArrayList<>(swiat.getOrganizmy());
            for (Organizm o : organizmySnapshot) {
                o.akcja();
            }

            swiat.wypiszLogi();
        }
    }
}
