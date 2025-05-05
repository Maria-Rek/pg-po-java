package Organizmy.Zwierzeta;

import Utils.Punkt;
import Organizmy.Zwierze;

import javax.swing.*;
import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Punkt polozenie) {
        super(polozenie, 4, 4);
    }

    @Override
    public String nazwa() {
        return "Owca";
    }

    @Override
    public Image getObrazek() {
        return new ImageIcon(getClass().getResource("/Resources/owca.png")).getImage();
    }
}
