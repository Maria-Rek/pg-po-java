package GUI;

import Swiat.SwiatKwadratowy;
import Organizmy.Organizm;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Widok extends JPanel {
    private final SwiatKwadratowy swiat;
    private final int rozmiarPola = 50;

    public Widok(SwiatKwadratowy swiat) {
        this.swiat = swiat;
        setPreferredSize(new Dimension(swiat.getSzerokosc() * rozmiarPola, swiat.getWysokosc() * rozmiarPola));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / rozmiarPola;
                int y = e.getY() / rozmiarPola;
                Punkt p = new Punkt(x, y);

                if (!swiat.czyPoleZajete(p)) {
                    // Tymczasowo dodaje losową roślinę (np. Trawa)
                    Organizmy.Rosliny.Trawa trawa = new Organizmy.Rosliny.Trawa(p);
                    swiat.dodajOrganizm(trawa);
                    swiat.dodajLog("Dodano Trawę na " + p);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysowanie siatki
        for (int i = 0; i <= swiat.getSzerokosc(); i++) {
            g.drawLine(i * rozmiarPola, 0, i * rozmiarPola, swiat.getWysokosc() * rozmiarPola);
        }
        for (int i = 0; i <= swiat.getWysokosc(); i++) {
            g.drawLine(0, i * rozmiarPola, swiat.getSzerokosc() * rozmiarPola, i * rozmiarPola);
        }

        // Rysowanie organizmów
        for (Organizm o : swiat.getOrganizmy()) {
            int x = o.getPolozenie().getX();
            int y = o.getPolozenie().getY();
            g.drawString(o.getIkona(), x * rozmiarPola + 15, y * rozmiarPola + 30);
        }
    }

    public void wykonajTure() {
        for (Organizm o : swiat.getOrganizmy()) {
            o.akcja();
        }
        repaint();
        swiat.wypiszLogi();
    }
}
