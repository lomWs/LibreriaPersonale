package gui.componenti;

import archivio.ArchivioLibri;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.*;
import query.QueryArchivioElimina;
import query.QueryArchivioIF;
import query.QueryArchivioModifica;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoxLibroDialog extends JDialog {

    private final TemaFactory tema;
    private final ArchivioLibri archivioLibri;
    private final Libro libro;

    public BoxLibroDialog(ArchivioLibri archivioLibri ,Frame framePropietario, Libro libro) {
        super(framePropietario, "Dettagli libro", true);
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.archivioLibri = archivioLibri;
        this.libro = libro;
        setSize(500, 500);
        setLocationRelativeTo(framePropietario);
        getContentPane().setBackground(tema.getColorePrimarioSfondo());
        setLayout(new BorderLayout());
        aggiungiComponentiGUI();


    }



    private void aggiungiComponentiGUI(){
        // Titolo principale
        JLabel titoloLabel = new JLabel(this.libro.getTitolo());
        titoloLabel.setFont(tema.getFontTitolo());
        titoloLabel.setForeground(tema.getColoreTesto());
        titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titoloLabel, BorderLayout.NORTH);

        // Pannello centrale con dettagli
        JPanel panelInfoLibro = new JPanel();
        panelInfoLibro.setLayout(new BoxLayout(panelInfoLibro, BoxLayout.Y_AXIS));
        panelInfoLibro.setBackground(tema.getColoreSecondarioSfondo());
        panelInfoLibro.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelInfoLibro.add(detail("Autori", formatAutori(this.libro.getAutori())));
        panelInfoLibro.add(detail("ISBN", this.libro.getISBN()));
        panelInfoLibro.add(detail("Stato", this.libro.getStato().toString()));
        panelInfoLibro.add(detail("Generi", formatGeneri(this.libro.getGeneri())));
        panelInfoLibro.add(detail("Valutazione", this.libro.getValutazione().formatoDisplay()));

        add(panelInfoLibro, BorderLayout.CENTER);
        // Sezione modifica stato e valutazione
        JCheckBox modificaBox = new JCheckBox("Modifica stato/valutazione");
        modificaBox.setFont(tema.getFontPrimario());
        modificaBox.setBackground(tema.getColoreSecondarioSfondo());
        modificaBox.setForeground(tema.getColoreTesto());

        JPanel modificaPanel = new JPanel();
        modificaPanel.setLayout(new BoxLayout(modificaPanel, BoxLayout.Y_AXIS));
        modificaPanel.setBackground(tema.getColoreSecondarioSfondo());
        modificaPanel.setVisible(false); // nascosto inizialmente

        JComboBox<StatoLibro> statoCombo = new JComboBox<>(StatoLibro.values());
        statoCombo.setSelectedItem(libro.getStato());
        statoCombo.setFont(tema.getFontPrimario());
        statoCombo.setBackground(tema.getColoreSecondarioSfondo().darker());
        statoCombo.setForeground(tema.getColoreTesto());

        JComboBox<ValutazioneLibro> valutazioneCombo = new JComboBox<>(ValutazioneLibro.values());
        valutazioneCombo.setSelectedItem(libro.getValutazione());
        valutazioneCombo.setFont(tema.getFontPrimario());
        valutazioneCombo.setBackground(tema.getColoreSecondarioSfondo().darker());
        valutazioneCombo.setForeground(tema.getColoreTesto());

        JButton salvaModifiche = tema.creaBottonePrincipale("Salva modifiche");
        salvaModifiche.addActionListener(e -> {
            libro.setStato((StatoLibro) statoCombo.getSelectedItem());
            libro.setValutazione((ValutazioneLibro) valutazioneCombo.getSelectedItem());
            QueryArchivioIF queryAggiorna = new QueryArchivioModifica(archivioLibri, libro);
            queryAggiorna.esegui();
            JOptionPane.showMessageDialog(this, "Libro aggiornato con successo.");
            dispose();
        });
        JScrollPane scrollPane = tema.creaScrollPane(panelInfoLibro);
        add(scrollPane, BorderLayout.CENTER);

        modificaPanel.add(creaComboBoxPanel("Nuovo stato", statoCombo));
        modificaPanel.add(Box.createVerticalStrut(10));
        modificaPanel.add(creaComboBoxPanel("Nuova valutazione", valutazioneCombo));
        modificaPanel.add(Box.createVerticalStrut(10));
        modificaPanel.add(salvaModifiche);

        modificaBox.addActionListener(e -> modificaPanel.setVisible(modificaBox.isSelected()));

        panelInfoLibro.add(Box.createVerticalStrut(20));
        panelInfoLibro.add(modificaBox);
        panelInfoLibro.add(modificaPanel);
        // Bottone elimina, con query
        JButton elimina = tema.creaBottoneElimina("Elimina");
        elimina.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler eliminare questo libro?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                QueryArchivioIF queryRimozione  = new QueryArchivioElimina(this.archivioLibri,this.libro.getISBN());
                queryRimozione.esegui();
                dispose();
            }
        });

        //Bottone chiusura Dialog
        JButton chiudi = tema.creaBottonePrincipale("Chiudi");
        chiudi.addActionListener(e -> dispose());

        //aggregazione dei due bottoni in un panel per formattali meglio nella gui
        JPanel pannelloPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pannelloPulsanti.setBackground(tema.getColorePrimarioSfondo());
        pannelloPulsanti.add(elimina);
        pannelloPulsanti.add(chiudi);
        add(pannelloPulsanti, BorderLayout.SOUTH);
    }


    private JPanel detail(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(tema.getColoreSecondarioSfondo());
        row.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JLabel lbl = new JLabel(label + ": ");
        lbl.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        lbl.setForeground(tema.getColoreTesto());

        JLabel val = new JLabel(value);
        val.setFont(tema.getFontPrimario());
        val.setForeground(tema.getColoreTesto());

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        return row;
    }

    //in caso di lista di autori li mostro separati da virgola
    private String formatAutori(List<Autore> autori) {
        return autori.stream()
                .map(a -> a.getNome() + " " + a.getCognome())
                .collect(Collectors.joining(", "));
    }

    //in caso di lista di generi li mostro separati da virgola
    private String formatGeneri(Set<GenereLibro> generi) {
        return generi.stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }



    private JPanel creaComboBoxPanel(String labelText, JComboBox<?> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(tema.getColoreSecondarioSfondo());
        JLabel label = new JLabel(labelText);
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        panel.add(label, BorderLayout.NORTH);

        comboBox.setBackground(tema.getColoreSecondarioSfondo().darker());
        comboBox.setForeground(tema.getColoreTesto());
        comboBox.setFont(tema.getFontPrimario());
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(comboBox, BorderLayout.CENTER);

        return panel;
    }


}


