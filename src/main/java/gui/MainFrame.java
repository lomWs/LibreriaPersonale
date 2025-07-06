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

    private final TemaFactory tema;
    private  GridBoxLibroPanel contentPanel;
    private  QueryBarPanel queryBar;
    private final ControllerLibro controller;

    public MainFrame(ControllerLibro controller) {

        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.controller =controller;
        //set per personalizzazioni grafiche
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
        this.contentPanel = new GridBoxLibroPanel(this.controller, (List<Libro>) controller.cerca());
        //archivio.aggiungiObserver(contentPanel); // registrazione observer

        // QueryBarPanel ha accesso all’archivio per eseguire query
        this.queryBar = new QueryBarPanel(this.controller);

        // ScrollPane tematizzato
        JScrollPane scrollPane = tema.creaScrollPane(contentPanel);

        // Aggiunta dei componenti
        add(queryBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }







}

