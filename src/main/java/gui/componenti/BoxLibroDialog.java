package gui.componenti;

import archivio.ArchivioLibri;
import gui.temi.GestoreTema;
import gui.temi.TemaFactory;
import model.Autore;
import model.GenereLibro;
import model.Libro;
import query.QueryArchivioElimina;
import query.QueryArchivioIF;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BoxLibroDialog extends JDialog {

    private final TemaFactory tema;
    private final ArchivioLibri archivioLibri;
    private final Libro libro;

    public BoxLibroDialog(ArchivioLibri archivioLibri ,Frame framePropietario, Libro libro) {
        super(framePropietario, "Dettagli libro", true);
        this.tema = GestoreTema.getInstance().getFactoryTemaAttuale();
        this.archivioLibri = archivioLibri;
        this.libro = libro;
        setSize(500, 500);
        setLocationRelativeTo(framePropietario);
        getContentPane().setBackground(tema.getColorePrimarioSfondo());
        setLayout(new BorderLayout());
        aggiungiComponentiGUI();


    }



    private void aggiungiComponentiGUI(){
        // Titolo principale
        JLabel titoloLabel = new JLabel(this.libro.getTitolo());
        titoloLabel.setFont(tema.getFontTitolo());
        titoloLabel.setForeground(tema.getColoreTesto());
        titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titoloLabel, BorderLayout.NORTH);

        // Pannello centrale con dettagli
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(tema.getColoreSecondarioSfondo());
        centro.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        centro.add(detail("Autori", formatAutori(this.libro.getAutori())));
        centro.add(detail("ISBN", this.libro.getISBN()));
        centro.add(detail("Stato", this.libro.getStato().toString()));
        centro.add(detail("Generi", formatGeneri(this.libro.getGeneri())));
        centro.add(detail("Valutazione", this.libro.getValutazione().formatoDisplay()));

        add(centro, BorderLayout.CENTER);

        // Bottone elimina, con query
        JButton elimina = tema.creaBottoneElimina("Elimina");
        elimina.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler eliminare questo libro?",
                    "Conferma eliminazione",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                QueryArchivioIF queryRimozione  = new QueryArchivioElimina(this.archivioLibri,this.libro.getISBN());
                queryRimozione.esegui();
                dispose();
            }
        });

        //Bottone chiusura Dialog
        JButton chiudi = tema.creaBottonePrincipale("Chiudi");
        chiudi.addActionListener(e -> dispose());

        //aggregazione dei due bottoni in un panel per formattali meglio nella gui
        JPanel pannelloPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pannelloPulsanti.setBackground(tema.getColorePrimarioSfondo());
        pannelloPulsanti.add(elimina);
        pannelloPulsanti.add(chiudi);
        add(pannelloPulsanti, BorderLayout.SOUTH);
    }


    private JPanel detail(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(tema.getColoreSecondarioSfondo());
        row.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JLabel lbl = new JLabel(label + ": ");
        lbl.setFont(tema.getFontPrimario().deriveFont(Font.BOLD));
        lbl.setForeground(tema.getColoreTesto());

        JLabel val = new JLabel(value);
        val.setFont(tema.getFontPrimario());
        val.setForeground(tema.getColoreTesto());

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        return row;
    }

    //in caso di lista di autori li mostro separati da virgola
    private String formatAutori(List<Autore> autori) {
        return autori.stream()
                .map(a -> a.getNome() + " " + a.getCognome())
                .collect(Collectors.joining(", "));
    }

    //in caso di lista di generi li mostro separati da virgola
    private String formatGeneri(Set<GenereLibro> generi) {
        return generi.stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}


