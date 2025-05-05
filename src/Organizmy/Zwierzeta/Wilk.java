package Organizmy.Zwierzeta;

import Organizmy.Zwierze;
import Utils.Punkt;

import javax.swing.*;
import java.awt.*;

public class Wilk extends Zwierze {
    public Wilk(Punkt polozenie) {
        super(polozenie, 9, 5);
    }

    @Override
    public String nazwa() {
        return "Wilk";
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/wilk.png")).getImage();
    }
}
