package gui.componenti;

import controller.ControllerLibro;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import query.filtro.*;

import javax.swing.*;
import java.awt.*;



public class QueryBarPanel extends JPanel {

    /**
     *  La classe QueryBarPanel è la barra che permette la ricerca, l'aggiunta/rimozione di filtri ed ordinamenti per i
     *  libri
     *
     * @See TemaFactory
     * @See Controller
     * @See JPanel
     * */


    private final TemaFactory tema;
    private final ControllerLibro controller;

    public QueryBarPanel(ControllerLibro controller) {
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.controller = controller;

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
        new NuovoLibroDialog(parent,controller).setVisible(true);
    }

    private void mostraDialogFiltri() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
        new FiltriDialog(parent, controller).setVisible(true);
    }

    private void eseguiRicerca(String testo) {

        if(!testo.isEmpty()) {
            CompositeFiltroArchivio filtroComposto = new CompositeFiltroArchivio("OR");
            filtroComposto=filtroComposto.aggiungi(new FiltroPerAutore(testo));
            filtroComposto=filtroComposto.aggiungi(new FiltroPerTitolo(testo));
            controller.cerca(filtroComposto);
        }else{
            controller.cerca();
        }
    }
}





