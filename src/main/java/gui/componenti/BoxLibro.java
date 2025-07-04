package gui.componenti;

import gui.ResourceLoader;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.ValutazioneLibro;

import javax.swing.*;
import java.awt.*;

public class BoxLibro extends JPanel {

    private final TemaFactory tema;

    public BoxLibro(String title, ValutazioneLibro valutazioneLibro, String imagePath) {
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();

        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(160, 220));
        setBackground(tema.getColoreSecondarioSfondo());
        setBorder(BorderFactory.createLineBorder(tema.getColorePrimarioSfondo(), 1));

        // Copertina
        ImageIcon icon = ResourceLoader.loadIcon(imagePath, 160, 180);
        JLabel cover = new JLabel(icon);
        cover.setHorizontalAlignment(SwingConstants.CENTER);
        cover.setVerticalAlignment(SwingConstants.CENTER);
        add(cover, BorderLayout.CENTER);

        // Pannello sotto la copertina con titolo + stelle
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(tema.getColoreSecondarioSfondo());

        // Titolo
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(label);

        // Stelle
        JLabel stelle = new JLabel(valutazioneLibro.formatoDisplay());
        stelle.setForeground(Color.YELLOW); // puoi usare tema.getColoreAccento() se vuoi
        stelle.setFont(tema.getFontPrimario());
        stelle.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(stelle);

        add(infoPanel, BorderLayout.SOUTH);
    }
}
