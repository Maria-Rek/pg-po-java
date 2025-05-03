package Swiat;

import Utils.Punkt;
import Organizmy.Organizm;
import java.util.*;
import java.lang.reflect.Constructor;

public class SwiatGlobalny {
    private static ISwiat instancja;

    public static void ustawSwiat(ISwiat swiat) {
        instancja = swiat;
    }

    public static ISwiat pobierzSwiat() {
        return instancja;
    }

    public static boolean czyPoleZajete(Punkt p) {
        return instancja.czyPoleZajete(p);
    }

    public static Organizm getOrganizmNa(Punkt p) {
        return instancja.znajdzOrganizm(p);
    }

    public static void usunOrganizm(Organizm o) {
        instancja.usunOrganizm(o);
    }

    public static void dodajOrganizm(Organizm o) {
        instancja.dodajOrganizm(o);
    }

    public static void dodajLog(String tekst) {
        instancja.dodajLog(tekst);
    }

    public static void stworzOrganizm(Class<? extends Organizm> typ, Punkt p) {
        try {
            Constructor<? extends Organizm> konstruktor = typ.getConstructor(Punkt.class);
            Organizm nowy = konstruktor.newInstance(p);
            dodajOrganizm(nowy);
        } catch (Exception e) {
            dodajLog("Błąd przy tworzeniu organizmu: " + e.getMessage());
        }
    }

    public static List<Punkt> getSasiedniePola(Punkt srodek) {
        List<Punkt> pola = new ArrayList<>();
        int szer = instancja.getSzerokosc();
        int wys = instancja.getWysokosc();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = srodek.getX() + dx;
                int ny = srodek.getY() + dy;
                if (nx >= 0 && nx < szer && ny >= 0 && ny < wys) {
                    pola.add(new Punkt(nx, ny));
                }
            }
        }
        return pola;
    }

    public static List<Punkt> getWolnePolaObok(Punkt srodek) {
        List<Punkt> sasiednie = getSasiedniePola(srodek);
        List<Punkt> wolne = new ArrayList<>();
        for (Punkt p : sasiednie) {
            if (!instancja.czyPoleZajete(p)) {
                wolne.add(p);
            }
        }
        return wolne;
    }
}
