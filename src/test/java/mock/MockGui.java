package mock;

import controller.ControllerLibro;
import model.Autore;
import model.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MockGui extends JPanel {

    private final ControllerLibro controller;
    private final JTextField campoISBN;
    private final JButton bottoneSalva;
    private final JButton bottoneElimina;


    public MockGui(ControllerLibro controller) {
        this.controller = controller;

        campoISBN = new JTextField(20);
        bottoneSalva = new JButton("Salva");
        bottoneElimina = new JButton("Elimina");

        setLayout(new FlowLayout());
        add(campoISBN);
        add(bottoneSalva);
        add(bottoneElimina);

        bottoneSalva.addActionListener(e -> {
            String ISBN = campoISBN.getText();
            if (ISBN != null && !ISBN.isBlank()) {
                Libro libro = new Libro.LibroBuilder(
                        List.of(new Autore("Test", "Mock")),
                        "TitololibroTest",
                        ISBN
                ).build();
                controller.aggiungi(libro);
            }
        });


        bottoneElimina.addActionListener(e -> {
            String ISBN = campoISBN.getText();
            controller.elimina(ISBN);

        });

    }

    public void setCampoISBN(String titolo) {
        campoISBN.setText(titolo);
    }

    public void cliccaBottoneAggiungi() {
        bottoneSalva.doClick();
    }

    public void cliccaBottoneElimina() {
        bottoneElimina.doClick();
    }


    }