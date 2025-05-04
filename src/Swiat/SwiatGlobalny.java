package Swiat;

import Utils.Punkt;
import Organizmy.Organizm;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ArrayList;

public class SwiatGlobalny {
    private static ISwiat instancja;
    private static JTextPane poleLogow;

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

    public static void setPoleLogow(JTextPane pole) {
        poleLogow = pole;
    }

    public static void dodajLog(String tekst) {
        if (poleLogow != null) {
            StyledDocument doc = poleLogow.getStyledDocument();
            SimpleAttributeSet styl = new SimpleAttributeSet();
            String lower = tekst.toLowerCase();

            Color kolor = new Color(199, 21, 133); // ciemny róż

            if (lower.contains("zgin") || lower.contains("zabity") || lower.contains("spalony") || lower.contains("atak")) {
                kolor = Color.RED;
            } else if (lower.contains("zamroż") || lower.contains("zamroz")) {
                kolor = new Color(0, 0, 139); // ciemnoniebieski
            } else if (lower.contains("rozsia")) {
                kolor = Color.GREEN.darker();
            } else if (lower.contains("rozmnoży") || lower.contains("rozmnoz")) {
                kolor = Color.ORANGE;
            }

            StyleConstants.setForeground(styl, kolor);

            try {
                doc.insertString(doc.getLength(), tekst + "\n", styl);
                poleLogow.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        System.out.println(tekst); // debugowy fallback
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

    // 🆕 Mapowanie nazw organizmów z pliku na klasy
    public static Class<? extends Organizm> klasaDlaNazwy(String nazwa) {
        return switch (nazwa) {
            case "Trawa" -> Organizmy.Rosliny.Trawa.class;
            case "Mlecz" -> Organizmy.Rosliny.Mlecz.class;
            case "Guarana" -> Organizmy.Rosliny.Guarana.class;
            case "WilczeJagody" -> Organizmy.Rosliny.WilczeJagody.class;
            case "BarszczSosnowskiego" -> Organizmy.Rosliny.BarszczSosnowskiego.class;
            case "Owca" -> Organizmy.Zwierzeta.Owca.class;
            case "Wilk" -> Organizmy.Zwierzeta.Wilk.class;
            case "Lis" -> Organizmy.Zwierzeta.Lis.class;
            case "Zolw" -> Organizmy.Zwierzeta.Zolw.class;
            case "Antylopa" -> Organizmy.Zwierzeta.Antylopa.class;
            case "CyberOwca" -> Organizmy.Zwierzeta.CyberOwca.class;
            case "Czlowiek" -> Organizmy.Zwierzeta.Czlowiek.class;
            default -> null;
        };
    }
}
