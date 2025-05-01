public enum Kierunek {
    GORA,
    DOL,
    LEWO,
    PRAWO,
    BRAK;

    public Punkt krok(Punkt start) {
        return switch (this) {
            case GORA -> new Punkt(start.getX(), start.getY() - 1);
            case DOL -> new Punkt(start.getX(), start.getY() + 1);
            case LEWO -> new Punkt(start.getX() - 1, start.getY());
            case PRAWO -> new Punkt(start.getX() + 1, start.getY());
            case BRAK -> new Punkt(start.getX(), start.getY());
        };
    }
}
