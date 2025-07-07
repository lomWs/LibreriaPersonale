package gui.componenti;

import controller.ControllerLibro;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.Libro;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class GridBoxLibroPanel extends JPanel implements Observer {


    /**
     *  La classe GridBoxLibroPanel contiene tutti i BoxLibro e si occupa della loro creazione e visualizzazione.GridBoxLibroPanel
     *  implementa Observer grazie al quale viene notificato degli aggiornamenti avvenuti e di conseguenza offre la
     *  lista dei BoxLibro consistenti con il backend
     *
     * @See TemaFactory
     * @See Controller
     * @See JPanel
     * @See Observer
     * */


    private final ControllerLibro controller;
    private final TemaFactory tema = GestoreTema.getInstance().getFactoryTemaAttuale();

    public GridBoxLibroPanel(ControllerLibro controller, List<Libro> libri) {
        this.controller = controller;
        setLayout(new GridLayout(0, 4, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(tema.getColorePrimarioSfondo());
        setOpaque(true);


        for (Libro libro : libri) {
            add(creaBox(libro));
        }
    }


    //metodo di creazione del singolo box
    private JPanel creaBox(Libro libro) {
        JPanel box = new BoxLibro(libro.getTitolo(),libro.getValutazione(),libro.getPercorsoCopertina());


        box.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frame finestraPrincipale = (Frame) SwingUtilities.getWindowAncestor(box);
                new BoxLibroDialog(GridBoxLibroPanel.this.controller,finestraPrincipale, libro).setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                box.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                box.setBackground(tema.getColoreTerziarioSfondo());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                box.setCursor(Cursor.getDefaultCursor());
                box.setBackground(tema.getColoreSecondarioSfondo());
            }
        });

        return box;
    }


    //metodo che permette il repaint() corretto dei libri
    @Override
    public void aggiorna(List<Libro> libri) {

        if(libri == null){

            libri = controller.cerca();

        }
        removeAll();
        for (Libro libro : libri) {
            add(creaBox(libro));
        }
        revalidate();
        repaint();

    }


}



