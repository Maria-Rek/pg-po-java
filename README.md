# Programowanie Obiektowe  
Object Oriented Programming Project Java (Swing GUI)  
PO – Politechnika Gdańska, Informatyka  

## Opis projektu
Projekt to gra symulacyjna w świecie 2D, w której organizmy żyją, poruszają się, walczą i rozmnażają.  
Każdy organizm ma unikalne zachowanie – niektóre poruszają się losowo, inne podejmują decyzje zależnie od otoczenia.

Graczem steruje **Człowiek** przy pomocy klawiatury (strzałki).  
Posiada on specjalną umiejętność – **całopalenie**, która przez 5 tur zabija organizmy wokół niego, a następnie ma 5 tur cooldownu.

W grze występuje również specjalne zwierzę – **CyberOwca**, która automatycznie szuka i niszczy niebezpieczny **Barszcz Sosnowskiego**.

Rozgrywka odbywa się w trybie turowym – organizmy wykonują akcje zgodnie z inicjatywą i wiekiem.  
Gra posiada GUI z planszą, logami wydarzeń, panelem informacji i obsługą zapisu/wczytywania stanu gry.

---

## Wymagania i funkcjonalności  
✅ Implementacja interfejsu `ISwiat` oraz klas organizmów  
✅ Dziedziczenie: `Zwierze`, `Roslina` z polimorfizmem i hermetyzacją  
✅ Implementacja 6 zwierząt:
- Wilk  
- Owca  
- Lis (unika silniejszych)  
- Żółw (25% szansy na ruch, odpiera słabszych)  
- Antylopa (zasięg 2, ucieczka 50%)  
- CyberOwca (szuka Barszczu)

✅ Implementacja 5 roślin:
- Trawa  
- Mlecz (rozsiewa się 3 razy)  
- Guarana (dodaje siłę +3)  
- Wilcze Jagody (trujące)  
- Barszcz Sosnowskiego (zabija wszystko wokół)

✅ **Człowiek**:
- Ruch: klawisze ←↑↓→  
- Umiejętność specjalna: całopalenie
  - 5 tur działania
  - 5 tur cooldownu
  - aktywacja spacją

✅ Obsługa kolizji i rozmnażania organizmów tego samego typu  
✅ Wykonanie tur wg inicjatywy i wieku  
✅ Ośmiosąsiedztwo przy ruchu i rozmnażaniu  
✅ Graficzna reprezentacja organizmów (ikony PNG)  
✅ Zapisywanie i wczytywanie gry z pliku tekstowego

---

## Struktura katalogów  
- `GUI/` – główne okno (`Gra.java`), plansza (`Widok.java`)  
- `Swiat/` – interfejs, logika świata, singleton  
- `Organizmy/` – klasy organizmów + podfoldery `Rosliny/`, `Zwierzeta/`  
- `Utils/` – pomocnicze klasy `Punkt`, `Kierunek`  
- `Resources/` – pliki graficzne organizmów  
- `Zapis/` – domyślny folder zapisu stanu gry  

---

## Uruchomienie
Projekt uruchamiany z klasy `main()` w `Gra.java`.  
Wymaga JDK 17+ oraz środowiska z obsługą Swing (np. IntelliJ IDEA).  
