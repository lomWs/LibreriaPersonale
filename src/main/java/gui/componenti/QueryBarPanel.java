package gui.componenti;

import archivio.ArchivioLibri;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import query.QueryArchivioCerca;
import query.QueryArchivioIF;
import query.filtro.*;

import javax.swing.*;
import java.awt.*;



public class QueryBarPanel extends JPanel {

    private final TemaFactory tema;
    private final ArchivioLibri archivioLibri;

    public QueryBarPanel(ArchivioLibri archivioLibri) {
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.archivioLibri = archivioLibri;

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setPreferredSize(new Dimension(1000, 70));
        setBackground(tema.getColorePrimarioSfondo());
        setOpaque(true);

        JTextField searchField = tema.creaTextField();
        searchField.setPreferredSize(new Dimension(320, 38));
        searchField.addActionListener(e -> eseguiRicerca(searchField.getText()));

        add(new JLabel("🔍"));
        add(searchField);

        JButton filtriButton = tema.creaBottonePrincipale("Filtri");
        filtriButton.addActionListener(e -> mostraDialogFiltri());

        JButton aggiungiButton = tema.creaBottonePrincipale("Aggiungi");
        aggiungiButton.addActionListener(e -> mostraDialogNuovoLibro());





        add(filtriButton);
        add(aggiungiButton);
    }

    private void mostraDialogNuovoLibro() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
        new NuovoLibroDialog(parent,archivioLibri).setVisible(true);
    }

    private void mostraDialogFiltri() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
        new FiltriDialog(parent, archivioLibri).setVisible(true);
    }

    private void eseguiRicerca(String testo) {
        // Potresti usare una QueryArchivioTestuale(archivioLibri, testo) da eseguire
        // Se il GridContentPanel osserva l'archivio, verrà aggiornato automaticamente
        if(!testo.isEmpty()) {
            CompositeFiltroArchivio filtroComposto = new CompositeFiltroArchivio("OR");
            filtroComposto=filtroComposto.aggiungi(new FiltroPerAutore(testo));
            filtroComposto=filtroComposto.aggiungi(new FiltroPerTitolo(testo));
            QueryArchivioIF queryRicerca = new QueryArchivioCerca(this.archivioLibri, filtroComposto);
            queryRicerca.esegui();
        }else{
            QueryArchivioIF queryRicerca = new QueryArchivioCerca(this.archivioLibri);
            queryRicerca.esegui();
        }
    }
}





