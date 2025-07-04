package gui.componenti;

import archivio.ArchivioLibri;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.GenereLibro;
import model.Libro;
import model.StatoLibro;
import model.ValutazioneLibro;
import query.QueryArchivioCerca;
import query.QueryArchivioIF;
import query.filtro.*;
import query.ordinamento.OrdinamentoArchivio;
import query.ordinamento.OrdinamentoPerAttributo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltriDialog extends JDialog {

    private final TemaFactory tema;
    private final ArchivioLibri archivio;
    private  JPanel filtriContainer;
    private  JComboBox<String> campoOrdinamentoCombo;
    private  JRadioButton ordineAsc;
    private  JRadioButton ordineDesc;
    private  JCheckBox attivaOrdinamento;

    public FiltriDialog(Frame frameProprietario, ArchivioLibri archivio) {
        super(frameProprietario, "Filtri di ricerca", true);
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.archivio = archivio;

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

        campoOrdinamentoCombo = new JComboBox<>(new String[]{"Titolo", "Valutazione"});
        campoOrdinamentoCombo.setFont(tema.getFontPrimario());
        campoOrdinamentoCombo.setBackground(tema.getColoreSecondarioSfondo());
        campoOrdinamentoCombo.setForeground(tema.getColoreTesto());

        ordineAsc = new JRadioButton("Ascendente");
        ordineDesc = new JRadioButton("Discendente");
        ButtonGroup group = new ButtonGroup();
        group.add(ordineAsc);
        group.add(ordineDesc);

        for (JRadioButton b : new JRadioButton[]{ordineAsc, ordineDesc}) {
            b.setBackground(tema.getColoreSecondarioSfondo());
            b.setForeground(tema.getColoreTesto());
            b.setFont(tema.getFontPrimario());
        }

        ordinamentoPanel.add(attivaOrdinamento);
        ordinamentoPanel.add(Box.createVerticalStrut(10));
        ordinamentoPanel.add(campoOrdinamentoCombo);
        ordinamentoPanel.add(ordineAsc);
        ordinamentoPanel.add(ordineDesc);

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
            // creo il filtro composto estraendo i singoli filtri inseriti dall'utente
            CompositeFiltroArchivio filtriComposti = estraiFiltriInseriti();

            if (attivaOrdinamento.isSelected()) {
                String campo = (String) campoOrdinamentoCombo.getSelectedItem();
                boolean ascendente = ordineAsc.isSelected();
                OrdinamentoArchivio ordinamento = switch (campo) {
                    case "Titolo" -> new OrdinamentoPerAttributo<>(Libro::getTitolo, ascendente);
                    case "Valutazione" -> new OrdinamentoPerAttributo<>(Libro::getValutazione, ascendente);
                    // aggiungi altri campi se ne supporti altri
                    default -> null;
                };

                QueryArchivioIF query = new QueryArchivioCerca(this.archivio,filtriComposti,ordinamento);
                query.esegui();
            }
            else{
                QueryArchivioIF query = new QueryArchivioCerca(this.archivio,filtriComposti);
                query.esegui();
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
        JComboBox<String> genereCombo = new JComboBox<>(Arrays.stream(GenereLibro.values()).map(Enum::name).toArray(String[]::new));
        JComboBox<String> statoCombo = new JComboBox<>(Arrays.stream(StatoLibro.values()).map(Enum::name).toArray(String[]::new));
        JComboBox<String> valutazioneCombo = new JComboBox<>(Arrays.stream(ValutazioneLibro.values()).map(Enum::name).toArray(String[]::new));

        configuraCombo(genereCombo);
        configuraCombo(statoCombo);
        configuraCombo(valutazioneCombo);

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

        JButton rimuovi = new JButton("Rimuovi");
        rimuovi.setFont(tema.getFontPrimario());
        rimuovi.setBackground(Color.RED.darker());
        rimuovi.setForeground(Color.WHITE);
        rimuovi.setPreferredSize(new Dimension(40, 40));
        rimuovi.setFocusPainted(false);
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

    private void configuraCombo(JComboBox<String> combo) {
        combo.setFont(tema.getFontPrimario());
        combo.setBackground(tema.getColoreSecondarioSfondo().darker());
        combo.setForeground(tema.getColoreTesto());
        combo.setPreferredSize(new Dimension(200, 40));
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
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
                }
            } catch (IllegalArgumentException ex) {
                System.err.println("Tipo di filtro non supportato");
            }
        }

        return filtroComposito;
    }

}




