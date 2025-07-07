package gui;

import controller.ControllerLibro;
import gui.componenti.GridBoxLibroPanel;
import gui.componenti.QueryBarPanel;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    /**
     * MainFrame è il Frame principale della GUI, fa da punto di accesso ai componenti della GUI, si occupa di
     * istanziare i due componenti prinicpali QueryBarPanel e GridBoxLibroPanel
     *
     * @See GridBoxLibroPanel
     * @See ControllerLibro
     * @See TemaFactory
     * @See QueryBarPanel
     * */



    private final TemaFactory tema;
    private  GridBoxLibroPanel gridBoxLibroPanel;
    private  QueryBarPanel queryBar;
    private final ControllerLibro controller;

    public MainFrame(ControllerLibro controller) {

        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.controller =controller;

        //personalizzazioni grafiche
        setTitle("Libreria personale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        inizializzaGUI();

    }



    private void inizializzaGUI(){

        getContentPane().setBackground(tema.getColorePrimarioSfondo());

        // GridContentPanel è observer: si iscrive e verrà notificato da archivio
        this.gridBoxLibroPanel = new GridBoxLibroPanel(this.controller, (List<Libro>) controller.cerca());

        controller.getArchivioLibri().aggiungiObserver(this.gridBoxLibroPanel);

        // QueryBarPanel ha accesso all’archivio per eseguire query
        this.queryBar = new QueryBarPanel(this.controller);

        // ScrollPane tematizzato
        JScrollPane scrollPane = tema.creaScrollPane(gridBoxLibroPanel);

        // Aggiunta dei componenti
        add(queryBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }



}

