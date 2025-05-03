package GUI;

import Swiat.SwiatKwadratowy;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;

public class Gra extends JFrame {
    private final Widok widok;
    private final int szerokosc = 10;
    private final int wysokosc = 10;

    public Gra() {
        setTitle("Wirtualny Świat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLayout(new BorderLayout());

        // Inicjalizacja świata i widoku
        SwiatKwadratowy swiat = new SwiatKwadratowy(szerokosc, wysokosc);
        SwiatGlobalny.ustawSwiat(swiat);

        widok = new Widok(swiat);
        add(widok, BorderLayout.CENTER);

        JPanel przyciski = new JPanel();
        JButton turaBtn = new JButton("Następna tura");
        przyciski.add(turaBtn);

        turaBtn.addActionListener(e -> {
            widok.wykonajTure();
        });

        add(przyciski, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gra::new);
    }
}
