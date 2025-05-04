package GUI;

import Swiat.SwiatKwadratowy;
import Swiat.SwiatGlobalny;

import javax.swing.*;
import java.awt.*;

public class Gra extends JFrame {
    private Widok widok;
    private SwiatKwadratowy swiat;
    private int maksTury;
    private final JLabel infoLabel;
    private final JTextPane poleLogow;

    public Gra() {
        setTitle("Wirtualny Świat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int szerokosc = 10;
        int wysokosc = 10;
        int tury = 10;

        // 🔄 POLSKIE PRZYCISKI: Tak / Nie
        Object[] options = {"Tak", "Nie"};
        int opcja = JOptionPane.showOptionDialog(null,
                "Czy chcesz wczytać zapisaną grę?",
                "Wczytaj grę",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (opcja == JOptionPane.YES_OPTION) {
            String nazwa = JOptionPane.showInputDialog(null, "Podaj nazwę pliku do wczytania:", "save");
            swiat = SwiatKwadratowy.wczytajZPliku(nazwa);
            if (swiat == null) {
                JOptionPane.showMessageDialog(null, "Nie udało się wczytać gry. Tworzona nowa.");
                swiat = new SwiatKwadratowy(szerokosc, wysokosc);
                swiat.setMaksTury(tury);
            }
        } else {
            try {
                JTextField szerField = new JTextField("10");
                JTextField wysField = new JTextField("10");
                JTextField turField = new JTextField("10");

                JPanel panel = new JPanel(new GridLayout(3, 2));
                panel.add(new JLabel("Szerokość:"));
                panel.add(szerField);
                panel.add(new JLabel("Wysokość:"));
                panel.add(wysField);
                panel.add(new JLabel("Liczba tur:"));
                panel.add(turField);

                int wynik = JOptionPane.showConfirmDialog(null, panel, "Nowa gra", JOptionPane.OK_CANCEL_OPTION);

                if (wynik == JOptionPane.OK_OPTION) {
                    szerokosc = Integer.parseInt(szerField.getText());
                    wysokosc = Integer.parseInt(wysField.getText());
                    tury = Integer.parseInt(turField.getText());

                    if (szerokosc <= 0 || wysokosc <= 0 || tury <= 0)
                        throw new NumberFormatException();
                } else {
                    throw new Exception();
                }

                swiat = new SwiatKwadratowy(szerokosc, wysokosc);
                swiat.setMaksTury(tury);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Niepoprawne dane. Użyto domyślnych wartości.");
                swiat = new SwiatKwadratowy(10, 10);
                swiat.setMaksTury(10);
            }

            swiat.dodajLog("Nowa gra została utworzona.");
        }

        this.maksTury = swiat.getMaksTury();
        SwiatGlobalny.ustawSwiat(swiat);

        // 🔵 Pasek informacyjny
        infoLabel = new JLabel("Witaj w grze Wirtualny Świat!");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(infoLabel, BorderLayout.NORTH);

        // 🟣 Pole logów
        poleLogow = new JTextPane();
        poleLogow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(poleLogow);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        add(scrollPane, BorderLayout.SOUTH);

        SwiatGlobalny.setPoleLogow(poleLogow);

        // 🟢 Widok świata
        widok = new Widok(swiat, maksTury, infoLabel);
        add(widok, BorderLayout.CENTER);

        // 🔘 Panel przycisków
        JPanel przyciski = new JPanel();
        JButton turaBtn = new JButton("Następna tura");
        JButton zapiszBtn = new JButton("Zapisz grę");
        JButton wczytajBtn = new JButton("Wczytaj grę");

        przyciski.add(turaBtn);
        przyciski.add(zapiszBtn);
        przyciski.add(wczytajBtn);

        turaBtn.addActionListener(e -> {
            widok.wykonajTure();

            if (swiat.getNumerTury() >= maksTury) {
                JOptionPane.showMessageDialog(this, "Gra zakończona po " + maksTury + " turach.");
                System.exit(0);
            }

            widok.requestFocusInWindow();
        });

        zapiszBtn.addActionListener(e -> {
            String nazwa = JOptionPane.showInputDialog(this, "Podaj nazwę pliku do zapisu:", "save");
            if (nazwa != null && !nazwa.trim().isEmpty()) {
                swiat.zapiszDoPliku(nazwa.trim());
            }
            widok.requestFocusInWindow();
        });

        wczytajBtn.addActionListener(e -> {
            String nazwa = JOptionPane.showInputDialog(this, "Podaj nazwę pliku do wczytania:", "save");
            if (nazwa != null && !nazwa.trim().isEmpty()) {
                SwiatKwadratowy nowy = SwiatKwadratowy.wczytajZPliku(nazwa.trim());
                if (nowy != null) {
                    remove(widok);
                    swiat = nowy;
                    maksTury = nowy.getMaksTury();
                    SwiatGlobalny.ustawSwiat(nowy);
                    widok = new Widok(swiat, maksTury, infoLabel);
                    add(widok, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                    SwiatGlobalny.dodajLog("Wczytano nową grę z pliku.");
                } else {
                    JOptionPane.showMessageDialog(this, "Nie udało się wczytać pliku.");
                }
                widok.requestFocusInWindow();
            }
        });

        add(przyciski, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(() -> widok.requestFocusInWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gra::new);
    }
}
