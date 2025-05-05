package GUI;

import Swiat.SwiatKwadratowy;
import Swiat.SwiatGlobalny;
import Organizmy.Organizm;
import Organizmy.Zwierzeta.Czlowiek;
import Utils.Kierunek;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Widok extends JPanel {
    private final SwiatKwadratowy swiat;
    private final int maxTury;
    private final int rozmiarPola = 50;
    private Punkt kliknietePole;
    private final JPopupMenu menuDodawania;
    private final JLabel infoLabel;

    public Widok(SwiatKwadratowy swiat, int maxTury, JLabel infoLabel) {
        this.swiat = swiat;
        this.maxTury = maxTury;
        this.infoLabel = infoLabel;

        setPreferredSize(new Dimension(swiat.getSzerokosc() * rozmiarPola, swiat.getWysokosc() * rozmiarPola));
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();

        SwiatGlobalny.ustawPanelWidoku(this); // 🔥 wymagane do repaintowania

        menuDodawania = new JPopupMenu();
        dodajOpcje("Trawa", Organizmy.Rosliny.Trawa.class);
        dodajOpcje("Mlecz", Organizmy.Rosliny.Mlecz.class);
        dodajOpcje("Guarana", Organizmy.Rosliny.Guarana.class);
        dodajOpcje("WilczeJagody", Organizmy.Rosliny.WilczeJagody.class);
        dodajOpcje("BarszczSosnowskiego", Organizmy.Rosliny.BarszczSosnowskiego.class);
        dodajOpcje("Owca", Organizmy.Zwierzeta.Owca.class);
        dodajOpcje("Wilk", Organizmy.Zwierzeta.Wilk.class);
        dodajOpcje("Lis", Organizmy.Zwierzeta.Lis.class);
        dodajOpcje("Zolw", Organizmy.Zwierzeta.Zolw.class);
        dodajOpcje("Antylopa", Organizmy.Zwierzeta.Antylopa.class);
        dodajOpcje("CyberOwca", Organizmy.Zwierzeta.CyberOwca.class);
        dodajOpcje("Czlowiek", Organizmy.Zwierzeta.Czlowiek.class);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / rozmiarPola;
                int y = e.getY() / rozmiarPola;

                if (x < 0 || x >= swiat.getSzerokosc() || y < 0 || y >= swiat.getWysokosc()) return;

                kliknietePole = new Punkt(x, y);
                Organizm o = swiat.znajdzOrganizm(kliknietePole);

                if (o == null) {
                    menuDodawania.show(Widok.this, e.getX(), e.getY());
                } else {
                    String info = "Organizm: " + o.nazwa() +
                            " | Pozycja: " + o.getPolozenie() +
                            " | Wiek: " + o.getWiek() +
                            " | Inicjatywa: " + o.getInicjatywa();
                    SwiatGlobalny.dodajLog(info);
                }

                requestFocusInWindow();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Czlowiek cz = znajdzCzlowieka();
                if (cz == null) {
                    SwiatGlobalny.dodajLog("Brak Człowieka w świecie.");
                    return;
                }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> cz.zaplanujRuch(Kierunek.GORA);
                    case KeyEvent.VK_DOWN -> cz.zaplanujRuch(Kierunek.DOL);
                    case KeyEvent.VK_LEFT -> cz.zaplanujRuch(Kierunek.LEWO);
                    case KeyEvent.VK_RIGHT -> cz.zaplanujRuch(Kierunek.PRAWO);
                    case KeyEvent.VK_SPACE -> cz.aktywujSpecjalna();
                }
            }
        });
    }

    private Czlowiek znajdzCzlowieka() {
        for (Organizm o : swiat.getOrganizmy()) {
            if (o instanceof Czlowiek cz) {
                return cz;
            }
        }
        return null;
    }

    private void dodajOpcje(String nazwa, Class<? extends Organizm> klasa) {
        JMenuItem item = new JMenuItem(nazwa);
        item.addActionListener(e -> {
            if (klasa == Czlowiek.class && znajdzCzlowieka() != null) {
                SwiatGlobalny.dodajLog("Na planszy już znajduje się Człowiek. Nie można dodać drugiego.");
                return;
            }
            try {
                Organizm nowy = klasa.getDeclaredConstructor(Punkt.class).newInstance(kliknietePole);
                swiat.dodajOrganizm(nowy);
                SwiatGlobalny.dodajLog("Dodano: " + nowy.nazwa() + " na " + kliknietePole);
                repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
                SwiatGlobalny.dodajLog("Błąd przy dodawaniu organizmu: " + nazwa);
            }
        });
        menuDodawania.add(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < swiat.getSzerokosc(); x++) {
            for (int y = 0; y < swiat.getWysokosc(); y++) {
                g.setColor(Color.WHITE);
                g.fillRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
                g.setColor(Color.GRAY);
                g.drawRect(x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola);
            }
        }

        List<Organizm> rosliny = new ArrayList<>();
        List<Organizm> zwierzeta = new ArrayList<>();

        for (Organizm o : swiat.getOrganizmy()) {
            if (o.getClass().getPackageName().contains("Rosliny")) {
                rosliny.add(o);
            } else {
                zwierzeta.add(o);
            }
        }

        for (Organizm o : rosliny) rysujOrganizm(g, o);
        for (Organizm o : zwierzeta) rysujOrganizm(g, o);
    }

    private void rysujOrganizm(Graphics g, Organizm o) {
        int x = o.getPolozenie().getX();
        int y = o.getPolozenie().getY();
        Image obrazek = o.getObrazek();
        if (obrazek != null) {
            g.drawImage(obrazek, x * rozmiarPola, y * rozmiarPola, rozmiarPola, rozmiarPola, this);
        }
    }

    public void wykonajTure() {
        List<Organizm> snapshot = new ArrayList<>(swiat.getOrganizmy());

        snapshot.sort(Comparator
                .comparingInt(Organizm::getInicjatywa).reversed()
                .thenComparingInt(Organizm::getWiek).reversed());

        for (Organizm o : snapshot) {
            if (swiat.getOrganizmy().contains(o)) {
                o.akcja();
            }
        }

        swiat.zwiekszNumerTury();
        repaint();
        aktualizujInfoLabel();
    }

    private void aktualizujInfoLabel() {
        String tekst = "Tura: " + swiat.getNumerTury() +
                " | Żyjące organizmy: " + swiat.getOrganizmy().size() +
                " | Pozostało tur: " + (maxTury - swiat.getNumerTury());

        Czlowiek cz = znajdzCzlowieka();
        if (cz != null) {
            tekst += " | Umiejętność: " + cz.getStatusTekst();
        } else {
            tekst += " | Człowiek nieobecny";
        }

        infoLabel.setText(tekst);
    }
}
