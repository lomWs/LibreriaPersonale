package gui.componenti;

import controller.ControllerLibro;
import gui.GestoreCopertina;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.*;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoxLibroDialog extends JDialog {

    /**
     *  La classe BoxLibroDialog viene istanziata a seguito di un evento, l'evento in questone è il click sul BoxLibro.
     *  La BoxLibroDialog mostra le infomrazioni relative al @param libro passato nel costruttore e permette di
     *  modificare stato/valutazione del libro e l'eliminazione dello stesso. Come da design l'interfaccia di riferimento
     *  per la relazione è il controller
     *
     *
     * @See TemaFactory
     * @See Controller
     * @See JPanel
     * @See GestoreCopertina
     * */


    private final TemaFactory tema;
    private final ControllerLibro controller;
    private final Libro libro;

    public BoxLibroDialog(ControllerLibro controller ,Frame framePropietario, Libro libro) {
        super(framePropietario, "Dettagli libro", true);
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.controller = controller;
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


          JComboBox<StatoLibro> statoCombo = tema.creaComboBox(StatoLibro.values());
          statoCombo.setSelectedItem(libro.getStato());


        JComboBox<ValutazioneLibro> valutazioneCombo = tema.creaComboBox(ValutazioneLibro.values());
        valutazioneCombo.setSelectedItem(libro.getValutazione());

        JButton salvaModifiche = tema.creaBottonePrincipale("Salva modifiche");
        salvaModifiche.addActionListener(e -> {
            libro.setStato((StatoLibro) statoCombo.getSelectedItem());
            libro.setValutazione((ValutazioneLibro) valutazioneCombo.getSelectedItem());

            controller.modifica(libro);

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


                controller.elimina(this.libro.getISBN());
                GestoreCopertina.eliminaCopertina(this.libro.getPercorsoCopertina());
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


    /**
     * Funzione detail che aggiunge settaggi grafici agiguntivi
     * */
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


    /**
     * Combo box agiguntiva nel caso in cui sia selezionata la possibità dall'utente di modificare stato/valutazione
     * */
    private JPanel creaComboBoxPanel(String labelText, JComboBox<?> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(tema.getColoreSecondarioSfondo());
        JLabel label = new JLabel(labelText);
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        panel.add(label, BorderLayout.NORTH);

        panel.add(comboBox, BorderLayout.CENTER);

        return panel;
    }


}


