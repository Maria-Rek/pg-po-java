package GUI;

import Swiat.SwiatKwadratowy;
import Swiat.SwiatGlobalny;
import Organizmy.Organizm;
import Organizmy.Zwierzeta.Czlowiek;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Gra extends JFrame {
    private Widok widok;
    private SwiatKwadratowy swiat;
    private int maksTury;
    private final JLabel infoLabel;
    private final JTextPane poleLogow;

    public Gra() {
        setTitle("Maria Rek 203174");
        setIconImage(new ImageIcon("src/Resources/logo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(227, 152, 214));
        setLayout(new BorderLayout());

        int szerokosc = 10;
        int wysokosc = 10;
        int tury = 10;

        Object[] options = {"Wczytaj grę", "Nowa gra"};
        int opcja = JOptionPane.showOptionDialog(null,
                "Co chcesz zrobić?",
                "Wczytywanie gry",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (opcja == 0) {
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
                panel.setBackground(new Color(227, 152, 214));
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

                Object[] sposoby = {"Ręcznie", "Losowo"};
                int wybor = JOptionPane.showOptionDialog(null,
                        "Jak chcesz dodać organizmy?",
                        "Dodawanie organizmów",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sposoby,
                        sposoby[0]);

                if (wybor == 1) dodajLosoweOrganizmy(swiat);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Niepoprawne dane. Użyto domyślnych wartości.");
                swiat = new SwiatKwadratowy(10, 10);
                swiat.setMaksTury(10);
            }

            swiat.dodajLog("Nowa gra została utworzona.");
        }

        this.maksTury = swiat.getMaksTury();
        SwiatGlobalny.ustawSwiat(swiat);

        infoLabel = new JLabel("Witaj w grze Wirtualny Świat!");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(infoLabel, BorderLayout.NORTH);

        poleLogow = new JTextPane();
        poleLogow.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(poleLogow);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        add(scrollPane, BorderLayout.SOUTH);

        SwiatGlobalny.setPoleLogow(poleLogow);

        widok = new Widok(swiat, maksTury, infoLabel);
        add(widok, BorderLayout.CENTER);

        JPanel przyciski = new JPanel();
        przyciski.setBackground(new Color(227, 152, 214));
        JButton turaBtn = new JButton("Następna tura");
        JButton zapiszBtn = new JButton("Zapisz grę");
        JButton wczytajBtn = new JButton("Wczytaj grę");
        JButton nowaGraBtn = new JButton("Nowa gra");

        Color kolorPrzyciskow = new Color(227, 127, 210);
        for (JButton btn : new JButton[]{turaBtn, zapiszBtn, wczytajBtn, nowaGraBtn}) {
            btn.setBackground(kolorPrzyciskow);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
        }

        przyciski.add(turaBtn);
        przyciski.add(zapiszBtn);
        przyciski.add(wczytajBtn);
        przyciski.add(nowaGraBtn);

        turaBtn.addActionListener(e -> {
            widok.wykonajTure();

            if (swiat.getNumerTury() >= maksTury) {
                SwingUtilities.invokeLater(this::pokazMenuKoncowe);
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

        nowaGraBtn.addActionListener(e -> {
            dispose();
            new Gra();
        });

        add(przyciski, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(() -> widok.requestFocusInWindow());
    }

    private void pokazMenuKoncowe() {
        Object[] opcje = {"Zakończ", "Nowa gra", "Wczytaj grę"};
        int wybor = JOptionPane.showOptionDialog(this,
                "Gra zakończona po " + maksTury + " turach.\nCo chcesz teraz zrobić?",
                "Koniec gry",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcje,
                opcje[0]);

        switch (wybor) {
            case 0 -> System.exit(0);
            case 1 -> {
                dispose();
                new Gra();
            }
            case 2 -> {
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
                }
            }
        }
    }

    private void dodajLosoweOrganizmy(SwiatKwadratowy swiat) {
        List<Class<? extends Organizm>> klasy = List.of(
                Organizmy.Rosliny.Trawa.class,
                Organizmy.Rosliny.Mlecz.class,
                Organizmy.Rosliny.Guarana.class,
                Organizmy.Rosliny.WilczeJagody.class,
                Organizmy.Rosliny.BarszczSosnowskiego.class,
                Organizmy.Zwierzeta.Owca.class,
                Organizmy.Zwierzeta.Wilk.class,
                Organizmy.Zwierzeta.Lis.class,
                Organizmy.Zwierzeta.Zolw.class,
                Organizmy.Zwierzeta.Antylopa.class,
                Organizmy.Zwierzeta.CyberOwca.class
        );

        int poleIlosc = swiat.getSzerokosc() * swiat.getWysokosc();
        int organizmyDoDodania = (int) (poleIlosc * (0.2 + Math.random() * 0.15));
        Set<Punkt> zajete = new HashSet<>();

        Random rand = new Random();

        while (true) {
            int x = rand.nextInt(swiat.getSzerokosc());
            int y = rand.nextInt(swiat.getWysokosc());
            Punkt p = new Punkt(x, y);
            if (!zajete.contains(p)) {
                swiat.dodajOrganizm(new Czlowiek(p));
                zajete.add(p);
                break;
            }
        }

        for (int i = 0; i < organizmyDoDodania; i++) {
            int x = rand.nextInt(swiat.getSzerokosc());
            int y = rand.nextInt(swiat.getWysokosc());
            Punkt p = new Punkt(x, y);
            if (zajete.contains(p)) {
                i--; // ponów próbę
                continue;
            }

            Class<? extends Organizm> klasa = klasy.get(rand.nextInt(klasy.size()));
            try {
                Organizm o = klasa.getDeclaredConstructor(Punkt.class).newInstance(p);
                swiat.dodajOrganizm(o);
                zajete.add(p);
            } catch (Exception ignored) {}
        }

        swiat.dodajLog("Wylosowano " + (organizmyDoDodania + 1) + " organizmów (w tym Człowiek).");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gra::new);
    }
}
