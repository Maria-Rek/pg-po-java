package Organizmy.Rosliny;

import Organizmy.Roslina;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(Punkt polozenie) {
        super(polozenie, 0);
    }

    @Override
    public String nazwa() {
        return "Trawa";
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/trawa.png")).getImage();
    }
}
