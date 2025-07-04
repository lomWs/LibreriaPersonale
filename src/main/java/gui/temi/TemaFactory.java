package gui.temi;

import javax.swing.*;
import java.awt.*;

public interface TemaFactory {


    JButton creaBottonePrincipale(String text);
    JButton creaBottoneElimina(String text);
    JTextField creaTextField();
    JComboBox<?> creaComboBox();
    JScrollBar creaScrollBar();
    JScrollPane creaScrollPane(Component view);


    Color getColorePrimarioSfondo();
    Color getColoreSecondarioSfondo();
    Color getColorePrimarioBottone();
    Color getColoreTesto();
    Color getColoreHover();

    // Font base
    Font getFontPrimario();
    Font getFontTitolo();

    Color getColoreTerziarioSfondo();
}
