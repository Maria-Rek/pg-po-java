package Swiat;

import Organizmy.Organizm;
import Utils.Punkt;
import Swiat.SwiatGlobalny;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SwiatKwadratowy implements ISwiat {
    private final int szerokosc;
    private final int wysokosc;
    private final List<Organizm> organizmy = new ArrayList<>();
    private final List<String> logi = new ArrayList<>();

    private int numerTury = 0;
    private int maksTury = 0;

    public SwiatKwadratowy(int szerokosc, int wysokosc) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
    }

    @Override
    public int getSzerokosc() {
        return szerokosc;
    }

    @Override
    public int getWysokosc() {
        return wysokosc;
    }

    @Override
    public boolean czyPoleZajete(Punkt p) {
        return znajdzOrganizm(p) != null;
    }

    @Override
    public Organizm znajdzOrganizm(Punkt p) {
        for (Organizm o : organizmy) {
            if (o.getPolozenie().equals(p)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void dodajOrganizm(Organizm o) {
        organizmy.add(o);
    }

    @Override
    public void usunOrganizm(Organizm o) {
        organizmy.remove(o);
    }

    @Override
    public void dodajLog(String log) {
        logi.add(log);
    }

    @Override
    public void wypiszLogi() {
        for (String log : logi) {
            SwiatGlobalny.dodajLog(log);
        }
        logi.clear();
    }

    @Override
    public List<Organizm> getOrganizmy() {
        return organizmy;
    }

    public int getNumerTury() {
        return numerTury;
    }

    public void zwiekszNumerTury() {
        numerTury++;
    }

    public void resetujTury() {
        numerTury = 0;
    }

    public int getMaksTury() {
        return maksTury;
    }

    public void setMaksTury(int maksTury) {
        this.maksTury = maksTury;
    }

    public void zapiszDoPliku(String nazwa) {
        File plik = new File("src/Zapis/" + nazwa + ".txt");
        try (PrintWriter writer = new PrintWriter(plik)) {
            writer.println("ROZMIAR " + szerokosc + " " + wysokosc);
            writer.println("TURA " + numerTury);
            writer.println("MAX_TURY " + maksTury);
            for (Organizm o : organizmy) {
                writer.println(o.nazwa() + " " + o.getPolozenie().getX() + " " + o.getPolozenie().getY() + " " + o.getWiek());
            }
            SwiatGlobalny.dodajLog("Zapisano stan gry do pliku " + plik.getPath());
        } catch (IOException e) {
            SwiatGlobalny.dodajLog("Błąd zapisu pliku: " + e.getMessage());
        }
    }

    public static SwiatKwadratowy wczytajZPliku(String nazwa) {
        File plik = new File("src/Zapis/" + nazwa + ".txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(plik))) {
            String linia = reader.readLine();
            if (linia == null || !linia.startsWith("ROZMIAR")) throw new IOException("Nieprawidłowy format pliku!");

            String[] tokens = linia.split(" ");
            int szer = Integer.parseInt(tokens[1]);
            int wys = Integer.parseInt(tokens[2]);

            SwiatKwadratowy swiat = new SwiatKwadratowy(szer, wys);

            linia = reader.readLine();
            if (linia.startsWith("TURA")) {
                swiat.numerTury = Integer.parseInt(linia.split(" ")[1]);
            }

            linia = reader.readLine();
            if (linia.startsWith("MAX_TURY")) {
                swiat.setMaksTury(Integer.parseInt(linia.split(" ")[1]));
            }

            String organizmLinia;
            while ((organizmLinia = reader.readLine()) != null) {
                String[] pola = organizmLinia.split(" ");
                String nazwaOrganizmu = pola[0];
                int x = Integer.parseInt(pola[1]);
                int y = Integer.parseInt(pola[2]);
                int wiek = Integer.parseInt(pola[3]);

                Punkt p = new Punkt(x, y);
                Class<? extends Organizm> klasa = SwiatGlobalny.klasaDlaNazwy(nazwaOrganizmu);
                if (klasa != null) {
                    Organizm o = klasa.getConstructor(Punkt.class).newInstance(p);
                    o.setWiek(wiek);
                    swiat.dodajOrganizm(o);
                }
            }

            SwiatGlobalny.dodajLog("Wczytano stan gry z pliku " + plik.getPath());
            return swiat;
        } catch (Exception e) {
            SwiatGlobalny.dodajLog("Błąd wczytywania pliku: " + e.getMessage());
            return null;
        }
    }
}
