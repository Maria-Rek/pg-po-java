package Swiat;

import Organizmy.Organizm;
import Utils.Punkt;
import java.util.List;

public interface ISwiat {
    int getSzerokosc();
    int getWysokosc();
    boolean czyPoleZajete(Punkt p);
    Organizm znajdzOrganizm(Punkt p);
    void dodajOrganizm(Organizm o);
    void usunOrganizm(Organizm o);
    void dodajLog(String log);

    void wypiszLogi();
    List<Organizm> getOrganizmy();
}
