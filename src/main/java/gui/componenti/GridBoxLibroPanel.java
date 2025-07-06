package gui.componenti;

import archivio.ArchivioLibri;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.Libro;
import observer.Observer;
import observer.Subject;
import query.QueryArchivioCerca;
import query.QueryArchivioIF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class GridBoxLibroPanel extends JPanel implements Observer {
    private final  ArchivioLibri archivioLibri;
    private final TemaFactory tema = GestoreTema.getInstance().getFactoryTemaAttuale();
    public GridBoxLibroPanel(ArchivioLibri archivioLibri, List<Libro> libri) {
        this.archivioLibri = archivioLibri;
        setLayout(new GridLayout(0, 4, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(tema.getColorePrimarioSfondo());
        setOpaque(true);

        archivioLibri.aggiungiObserver(this);


        for (int i = 0; i < libri.size(); i++) {
            add(creaBox(libri.get(i)));
        }
    }

    private JPanel creaBox(Libro libro) {
        JPanel box = new BoxLibro(libro.getTitolo(),libro.getValutazione(),libro.getPercorsoCopertina());


        box.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(box);
                new BoxLibroDialog(GridBoxLibroPanel.this.archivioLibri,parent, libro).setVisible(true);
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

    public void aggiorna(List<Libro> libri) {

        if(libri == null){
            QueryArchivioIF q = new QueryArchivioCerca(archivioLibri);
             libri = (List<Libro>) q.esegui();

        }
        removeAll();
        for (Libro libro : libri) {
            add(creaBox(libro));
        }
        revalidate();
        repaint();

    }


}



