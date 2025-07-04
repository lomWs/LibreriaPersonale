package gui;

import archivio.ArchivioLibri;
import gui.componenti.GridBoxLibroPanel;
import gui.componenti.QueryBarPanel;
import gui.temi.GestoreTema;
import gui.temi.TemaChiaroFactory;
import gui.temi.TemaFactory;
import gui.temi.TemaScuroFactory;
import model.Libro;
import query.QueryArchivioCerca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final TemaFactory tema;
    private  GridBoxLibroPanel contentPanel;
    private  QueryBarPanel queryBar;
    private  ArchivioLibri archivioLibri;

    public MainFrame(ArchivioLibri archivio) {

        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.archivioLibri =archivio;
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
        this.contentPanel = new GridBoxLibroPanel(this.archivioLibri, (List<Libro>) new QueryArchivioCerca(this.archivioLibri).esegui());
        //archivio.aggiungiObserver(contentPanel); // registrazione observer

        // QueryBarPanel ha accesso all’archivio per eseguire query
        this.queryBar = new QueryBarPanel(this.archivioLibri);

        // ScrollPane tematizzato
        JScrollPane scrollPane = tema.creaScrollPane(contentPanel);

        // Aggiunta dei componenti
        add(queryBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }







}

