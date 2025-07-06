package gui.componenti;

import controller.ControllerLibro;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.GenereLibro;
import model.Libro;
import model.StatoLibro;
import model.ValutazioneLibro;
import query.filtro.*;
import query.ordinamento.OrdinamentoArchivio;
import query.ordinamento.OrdinamentoPerAttributo;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class FiltriDialog extends JDialog {

    private final TemaFactory tema;
    private final ControllerLibro controller;
    private  JPanel filtriContainer;
    private  JComboBox<String> campoOrdinamentoCombo;
    private  JRadioButton ordineCrescente;
    private  JRadioButton ordineDecrescente;
    private  JCheckBox attivaOrdinamento;

    public FiltriDialog(Frame frameProprietario, ControllerLibro controller) {
        super(frameProprietario, "Filtri ricerca", true);
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.controller = controller;

        setSize(520, 600);
        setLocationRelativeTo(frameProprietario);
        setLayout(new BorderLayout());
        getContentPane().setBackground(tema.getColorePrimarioSfondo());
        aggiungiComponentiGui();

    }

    private void aggiungiComponentiGui(){
        // Intestazione
        JLabel header = new JLabel("Applica filtri e ordinamento", SwingConstants.CENTER);
        header.setFont(tema.getFontTitolo());
        header.setForeground(tema.getColoreTesto());
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // Pannello centrale scrollabile con filtri
        filtriContainer = new JPanel();
        filtriContainer.setLayout(new BoxLayout(filtriContainer, BoxLayout.Y_AXIS));
        filtriContainer.setBackground(tema.getColoreSecondarioSfondo());
        filtriContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = tema.creaScrollPane(filtriContainer);

        JButton aggiungiFiltro = tema.creaBottonePrincipale("+ Aggiungi filtro");
        aggiungiFiltro.setAlignmentX(Component.LEFT_ALIGNMENT);
        aggiungiFiltro.addActionListener(e -> aggiungiFiltroPanel());

        // Spazio e bottone sotto i filtri
        JPanel aggiungiPanel = new JPanel();
        aggiungiPanel.setBackground(tema.getColoreSecondarioSfondo());
        aggiungiPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        aggiungiPanel.add(aggiungiFiltro);

        filtriContainer.add(Box.createVerticalStrut(10));
        filtriContainer.add(aggiungiPanel);

        // Ordinamento
        JPanel ordinamentoPanel = new JPanel();
        ordinamentoPanel.setLayout(new BoxLayout(ordinamentoPanel, BoxLayout.Y_AXIS));
        ordinamentoPanel.setBackground(tema.getColoreSecondarioSfondo());


        attivaOrdinamento = new JCheckBox("Attiva ordinamento");
        attivaOrdinamento.setBackground(tema.getColoreSecondarioSfondo());
        attivaOrdinamento.setForeground(tema.getColoreTesto());
        attivaOrdinamento.setFont(tema.getFontPrimario());


        JComboBox<String>campoOrdinamentoCombo = tema.creaComboBox(new String[]{"Titolo", "Valutazione"});


        ordineCrescente = new JRadioButton("Crescente");
        ordineDecrescente = new JRadioButton("Decrescente");
        ButtonGroup group = new ButtonGroup();
        group.add(ordineCrescente);
        group.add(ordineDecrescente);

        for (JRadioButton b : new JRadioButton[]{ordineCrescente, ordineDecrescente}) {
            b.setBackground(tema.getColoreSecondarioSfondo());
            b.setForeground(tema.getColoreTesto());
            b.setFont(tema.getFontPrimario());
        }

        ordinamentoPanel.add(attivaOrdinamento);
        ordinamentoPanel.add(Box.createVerticalStrut(10));
        ordinamentoPanel.add(campoOrdinamentoCombo);
        ordinamentoPanel.add(ordineCrescente);
        ordinamentoPanel.add(ordineDecrescente);

        // Centro = scroll filtri + ordinamento
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(tema.getColoreSecondarioSfondo());
        centro.add(scrollPane, BorderLayout.CENTER);
        centro.add(ordinamentoPanel, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);

        // Footer con bottoni
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(tema.getColorePrimarioSfondo());

        JButton annulla = tema.creaBottoneElimina("Annulla");
        annulla.addActionListener(e -> dispose());

        JButton applica = tema.creaBottonePrincipale("Applica");
        applica.addActionListener(e -> {
            CompositeFiltroArchivio filtriComposti = estraiFiltriInseriti();

            if (attivaOrdinamento.isSelected()) {
                String campo = (String) campoOrdinamentoCombo.getSelectedItem();
                boolean ascendente = ordineCrescente.isSelected();
                assert campo != null;
                OrdinamentoArchivio ordinamento = campo.equals("Titolo") ?
                        new OrdinamentoPerAttributo<>(Libro::getTitolo, ascendente) :
                        new OrdinamentoPerAttributo<>(Libro::getValutazione, ascendente);

                controller.cerca(filtriComposti,ordinamento);

            } else {
                controller.cerca(filtriComposti);

            }
            dispose();
        });

        footer.add(annulla);
        footer.add(applica);
        add(footer, BorderLayout.SOUTH);
    }



    private void aggiungiFiltroPanel() {
        JPanel filtroRow = new JPanel();
        filtroRow.setLayout(new BoxLayout(filtroRow, BoxLayout.X_AXIS));
        filtroRow.setBackground(tema.getColoreSecondarioSfondo());

        JComboBox<String> campoFiltro = new JComboBox<>(new String[]{
                "Titolo", "ISBN", "Autore", "Genere", "Stato", "Valutazione"
        });
        campoFiltro.setFont(tema.getFontPrimario());
        campoFiltro.setBackground(tema.getColoreSecondarioSfondo().darker());
        campoFiltro.setForeground(tema.getColoreTesto());

        JPanel valoreInputWrapper = new JPanel(new CardLayout());
        valoreInputWrapper.setBackground(tema.getColoreSecondarioSfondo());

        JTextField valoreTextField = tema.creaTextField();

        JComboBox<GenereLibro> genereCombo = tema.creaComboBox(GenereLibro.values());
        JComboBox<StatoLibro> statoCombo =tema.creaComboBox(StatoLibro.values());

        //serve perche il filtro viene creato in base al nome e non in base alle stelle della valutazione
        JComboBox<String> valutazioneCombo =tema.creaComboBox(Arrays
                .stream(ValutazioneLibro.values())
                .map(Enum::name)
                .toArray(String[]::new));



        valoreInputWrapper.add(valoreTextField, "testo");
        valoreInputWrapper.add(genereCombo, "genere");
        valoreInputWrapper.add(statoCombo, "stato");
        valoreInputWrapper.add(valutazioneCombo, "valutazione");

        CardLayout layout = (CardLayout) valoreInputWrapper.getLayout();
        layout.show(valoreInputWrapper, "testo");

        campoFiltro.addActionListener(e -> {
            String selected = (String) campoFiltro.getSelectedItem();
            switch (selected) {
                case "Genere" -> layout.show(valoreInputWrapper, "genere");
                case "Stato" -> layout.show(valoreInputWrapper, "stato");
                case "Valutazione" -> layout.show(valoreInputWrapper, "valutazione");
                default -> layout.show(valoreInputWrapper, "testo");
            }
        });

        JButton rimuovi = tema.creaBottoneElimina("Rimuovi");
        rimuovi.addActionListener(e -> {
            filtriContainer.remove(filtroRow);
            filtriContainer.revalidate();
            filtriContainer.repaint();
        });

        filtroRow.add(campoFiltro);
        filtroRow.add(Box.createHorizontalStrut(10));
        filtroRow.add(valoreInputWrapper);
        filtroRow.add(Box.createHorizontalStrut(10));
        filtroRow.add(rimuovi);

        // Inserisce prima del pannello dei pulsanti
        filtriContainer.add(filtroRow, filtriContainer.getComponentCount() - 1);
        filtriContainer.add(Box.createVerticalStrut(10), filtriContainer.getComponentCount() - 1);
        filtriContainer.revalidate();
        filtriContainer.repaint();
    }


    private CompositeFiltroArchivio estraiFiltriInseriti() {
        CompositeFiltroArchivio filtroComposito = new CompositeFiltroArchivio("AND");

        for (Component comp : filtriContainer.getComponents()) {
            if (!(comp instanceof JPanel filtroRow)) continue;

            Component[] children = filtroRow.getComponents();

            // Ignora righe che non hanno almeno: JComboBox campo, spazio, valoreWrapper, spazio, bottone rimuovi
            if (children.length < 3 || !(children[0] instanceof JComboBox<?> campoFiltro)) continue;

            JPanel valoreInputWrapper = (JPanel) children[2];
            CardLayout layout = (CardLayout) valoreInputWrapper.getLayout();

            String campoSelezionato = (String) campoFiltro.getSelectedItem();
            String valore = null;

            // Trova il componente attivo visibile (JTextField o JComboBox)
            for (Component input : valoreInputWrapper.getComponents()) {
                if (input.isVisible()) {
                    if (input instanceof JTextField textField) {
                        valore = textField.getText().trim();
                    } else if (input instanceof JComboBox<?> comboBox) {
                        Object selected = comboBox.getSelectedItem();
                        valore = selected != null ? selected.toString() : null;
                    }
                    break;
                }
            }

            if (valore == null || valore.isBlank()) continue;

            // Crea il filtro specifico
            try {
                switch (campoSelezionato) {
                    case "Titolo" -> filtroComposito = filtroComposito.aggiungi(new FiltroPerTitolo(valore));
                    case "ISBN" -> filtroComposito = filtroComposito.aggiungi(new FiltroPerISBN(valore));
                    case "Autore" -> filtroComposito = filtroComposito.aggiungi(new FiltroPerAutore(valore));
                    case "Genere" -> filtroComposito = filtroComposito.aggiungi(new FiltroPerGenere(GenereLibro.valueOf(valore)));
                    case "Stato" -> filtroComposito = filtroComposito.aggiungi(new FiltroPerStato(StatoLibro.valueOf(valore)));
                    case "Valutazione" -> filtroComposito = filtroComposito.aggiungi(new FiltroPerValutazione(ValutazioneLibro.valueOf(valore)));
                    case null -> {}
                    default -> throw new IllegalStateException("Valore non corretto " + campoSelezionato);
                }
            } catch (IllegalArgumentException ex) {
                System.err.println("Tipo di filtro non supportato "+ valore);
            }
        }

        return filtroComposito;
    }

}




