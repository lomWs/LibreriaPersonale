package gui.componenti;

import gui.GestoreCopertina;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.ValutazioneLibro;

import javax.swing.*;
import java.awt.*;

public class BoxLibro extends JPanel {

    private final TemaFactory tema;
    private final String titolo;
    private final ValutazioneLibro valutazione;
    private final String imagePath;

    public BoxLibro(String titolo, ValutazioneLibro valutazioneLibro, String imagePath) {
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.titolo=titolo;
        this.valutazione=valutazioneLibro;
        this.imagePath=imagePath;

        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(160, 220));
        setBackground(tema.getColoreSecondarioSfondo());
        setBorder(BorderFactory.createLineBorder(tema.getColorePrimarioSfondo(), 1));

        aggiungiComponentiGUI();

    }

    private void aggiungiComponentiGUI(){
        // Copertina
        ImageIcon icon = GestoreCopertina.loadIcon(this.imagePath, 160, 180);
        JLabel cover = new JLabel(icon);
        cover.setHorizontalAlignment(SwingConstants.CENTER);
        cover.setVerticalAlignment(SwingConstants.CENTER);
        add(cover, BorderLayout.CENTER);

        // Informazioni sotto l'immagine
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(tema.getColoreSecondarioSfondo());

        // Titolo
        JLabel label = new JLabel(this.titolo, SwingConstants.CENTER);
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(label);

        // Valutazione in giallo
        JLabel valutazioneLabel = new JLabel(this.valutazione.formatoDisplay());
        valutazioneLabel.setForeground(Color.YELLOW);
        valutazioneLabel.setFont(tema.getFontPrimario());
        valutazioneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(valutazioneLabel);

        add(infoPanel, BorderLayout.SOUTH);
    }


}
