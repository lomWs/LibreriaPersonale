import archivio.ArchivioLibri;
import archivio.ArchivioLibriJSON;
import controller.ControllerLibro;
import gui.MainFrame;
import mock.MockGui;
import model.Autore;
import model.GenereLibro;
import model.Libro;
import org.junit.jupiter.api.*;
import query.filtro.FiltroPerAutore;
import query.filtro.FiltroPerTitolo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuiControllerTest {

    private ArchivioLibri archivio;
    private ControllerLibro controller;
    private MockGui mockGui;
    private final String percorsoArchivio ="src/test/testArchivioGuiController.json";

    @BeforeAll
    void setUp() {
        archivio = new ArchivioLibriJSON(percorsoArchivio);
        controller = new ControllerLibro(archivio);
        mockGui = new MockGui(controller);

    }

    @Test
    void inserimentoLibroConController() {
        Libro libro = new Libro.LibroBuilder(List.of(new Autore("Jane", "Austen")), "Pride and Prejudice", "1234567890123")
                .generi(Set.of(GenereLibro.ROMANZO))
                .build();

        controller.aggiungi(libro);
        Assertions.assertEquals(1, controller.cerca(new FiltroPerTitolo("Pride and Pre")).size());
    }



    @Test
    void ricercaPerTitoloConController() {
        Libro libro = new Libro.LibroBuilder(List.of(new Autore("F. Scott", "Fitzgerald")), "The Great Gatsby", "9999999999999")
                .generi(Set.of(GenereLibro.ROMANZO))
                .build();
        controller.aggiungi(libro);

        var risultati = controller.cerca(new FiltroPerTitolo("Gatsby"));
        Assertions.assertFalse(risultati.isEmpty());
    }





    @Test
    public void testMockGuiAggiunta_RimozioneLibro() {

        mockGui.setCampoISBN("I456456453");
        mockGui.cliccaBottoneAggiungi();

        List<Libro> risultato = controller.cerca();

        Assertions.assertTrue(risultato.stream()
                .anyMatch(libro -> libro.getISBN().equals("I456456453")));


        mockGui.cliccaBottoneElimina();

        List<Libro> risDopoElim = controller.cerca();

        Assertions.assertTrue(risDopoElim.stream()
                .noneMatch(libro -> libro.getISBN().equals("I456456453")));


    }



    @AfterAll
    void eliminaTutto() throws IOException {
        Path path = Paths.get(percorsoArchivio);
        if (Files.exists(path)) {
            Files.delete(path);

        }
    }




}

