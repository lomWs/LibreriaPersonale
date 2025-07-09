package gui.componenti;

import controller.ControllerLibro;
import gui.GestoreCopertina;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NuovoLibroDialog extends JDialog {

    /**
     *  La classe NuovoLibroDialog viene creata a seguito di un evento, l'evento in questone è il click sul bottone aggiungi
     *  nella QueryBarPanel.
     *  La NuovoLibroDialog è l'unico punto di accesso da parte dell'utente di inserire un nuovo libro. Mette a disposisione
     *  tutti i campi e la possibilità di inserire una copertina, la scelta progettuale prevede massima libertà nella creazione
     *  anche di inserire un solo libro senza ISBN infatti l'unico controllo effettuato è sulla presenza di più ISBN uguali.
     *
     *
     * @See TemaFactory
     * @See Controller
     * @See JPanel
     * @See GestoreCopertina
     * */

    private String percorsoCopertina =" ";

    private  JTextField titoloField;
    private  JTextField isbnField;
    private  JList<GenereLibro> generiList;
    private  JComboBox<StatoLibro> statoLibroJComboBox;
    private  JComboBox<ValutazioneLibro> valutazioneCombo;
    private  JPanel autoriPanel;
    private final List<JTextField[]> autoriFields = new ArrayList<>();
    private final TemaFactory tema = GestoreTema.getInstance().getFactoryTemaAttuale();
    private final ControllerLibro controller;


    public NuovoLibroDialog(Frame framePropietario, ControllerLibro controller) {
        super(framePropietario, "Nuovo libro", true);

        this.controller = controller;
        setSize(520, 650);
        setLocationRelativeTo(framePropietario);
        setLayout(new BorderLayout());
        getContentPane().setBackground(tema.getColorePrimarioSfondo());
        aggiungiComponentiGUI();

    }


    // metodo per l'inizializzazione del JDialog, imposto le preferenze
    private void aggiungiComponentiGUI(){
        JLabel header = new JLabel("Aggiungi un nuovo libro");
        header.setFont(tema.getFontTitolo());
        header.setForeground(tema.getColoreTesto());
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(tema.getColoreSecondarioSfondo());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(creaFormField("Titolo", titoloField = tema.creaTextField()));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(creaFormField("ISBN", isbnField = tema.creaTextField()));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(creaSezioneAutori());
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(creaPannelloEnumerazione("Genere", generiList = tema.creaList(GenereLibro.values())));
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(creaComboBoxPanel("Stato", statoLibroJComboBox =  tema.creaComboBox(StatoLibro.values())));
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(creaComboBoxPanel("Valutazione", valutazioneCombo = tema.creaComboBox(ValutazioneLibro.values())));

        JScrollPane scrollPane = tema.creaScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton salva = tema.creaBottonePrincipale("Salva");

        salva.addActionListener(_ -> {
            String titolo = titoloField.getText().trim();
            String isbn = isbnField.getText().trim();
            Set<GenereLibro> generi = new HashSet<>(generiList.getSelectedValuesList());
            StatoLibro statoLibro = (StatoLibro) statoLibroJComboBox.getSelectedItem();
            ValutazioneLibro valutazione = (ValutazioneLibro) valutazioneCombo.getSelectedItem();


            List<Autore> autori = new ArrayList<>();
            for (JTextField[] fields : autoriFields) {
                String nome = fields[0].getText().trim();
                String cognome = fields[1].getText().trim();
                if (!nome.isEmpty() && !cognome.isEmpty()
                && !nome.equals("Nome") && !cognome.equals("Cognome")) {
                    autori.add(new Autore(nome, cognome));
                }
            }
            try {

                Libro nuovoLibro = new Libro.LibroBuilder(autori,titolo,isbn)
                        .generi(generi)
                        .valutazione(valutazione)
                        .stato(statoLibro)
                        .build();
                nuovoLibro.setPercorsoCopertina(this.percorsoCopertina);


                controller.aggiungi(nuovoLibro);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Errore di inserimento",
                        JOptionPane.ERROR_MESSAGE);
            }


            dispose();
        });

        JButton annulla = tema.creaBottoneElimina("Annulla");
        annulla.addActionListener(_ ->
            {
                GestoreCopertina.eliminaCopertina(this.percorsoCopertina);
                dispose();
            }
        );

        JButton bottoneCaricaCopertina = tema.creaBottonePrincipale("Carica copertina");

        bottoneCaricaCopertina.addActionListener(_ -> {
            // JFileChooser server per la selezione del file immagine
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleziona una copertina per il libro");

            // finestra di dialogo per scegliere un file
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // Se l'utente ha selezionato un file
                File selectedFile = fileChooser.getSelectedFile();

                this.percorsoCopertina = GestoreCopertina.salvataggioCopertina(selectedFile);

            }
        });


        JPanel bottom = new JPanel();
        bottom.setBackground(tema.getColorePrimarioSfondo());
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        bottom.add(annulla);
        bottom.add(salva);
        bottom.add(bottoneCaricaCopertina);

        add(bottom, BorderLayout.SOUTH);


    }


    // metodi privati per la gestione dei componenti interni al JDialog

    private JPanel creaFormField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(tema.getColoreSecondarioSfondo());

        JLabel label = new JLabel(labelText);
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        panel.add(label, BorderLayout.NORTH);


        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel creaSezioneAutori() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(tema.getColoreSecondarioSfondo());

        JLabel label = new JLabel("Autori");
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));

        autoriPanel = new JPanel();
        autoriPanel.setLayout(new BoxLayout(autoriPanel, BoxLayout.Y_AXIS));
        autoriPanel.setBackground(tema.getColoreSecondarioSfondo());
        panel.add(autoriPanel);

        JButton aggiungi = tema.creaBottonePrincipale("+ Aggiungi autore");
        aggiungi.setFont(tema.getFontPrimario());
        aggiungi.setAlignmentX(Component.LEFT_ALIGNMENT);
        aggiungi.addActionListener(_ -> aggiungiCampiAutore());
        panel.add(Box.createVerticalStrut(10));
        panel.add(aggiungi);

        aggiungiCampiAutore();

        return panel;
    }

    private void aggiungiCampiAutore() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setBackground(tema.getColoreSecondarioSfondo());

        JTextField nome = tema.creaTextField();
        JTextField cognome = tema.creaTextField();


        configuraPlaceholder(nome, "Nome");
        configuraPlaceholder(cognome, "Cognome");

        row.add(nome);
        row.add(cognome);
        autoriPanel.add(row);
        autoriFields.add(new JTextField[]{nome, cognome});

        autoriPanel.revalidate();
        autoriPanel.repaint();
    }

    private JPanel creaPannelloEnumerazione(String labelText, JList<?> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(tema.getColoreSecondarioSfondo());

        JLabel label = new JLabel(labelText);
        label.setForeground(tema.getColoreTesto());
        label.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        panel.add(label, BorderLayout.NORTH);

        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        JScrollPane scroll = tema.creaScrollPane(list);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

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


    //metodo per il comportamento del testo se cliccato (nome , cognome) sparisce il testo
    private void configuraPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }




}