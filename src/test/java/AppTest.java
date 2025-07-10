import archivio.ArchivioLibri;
import archivio.ArchivioLibriJSON;

import controller.ControllerLibro;
import gui.MainFrame;
import gui.componenti.GridBoxLibroPanel;
import gui.componenti.QueryBarPanel;
import model.Autore;
import model.Libro;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

    private ArchivioLibri archivio;
    private ControllerLibro controller;
    private MainFrame mainFrame;
    private final Path archivioPath = Paths.get("src/test/testArchivio.json");

    @BeforeAll
    void setUp() {
        archivio = new ArchivioLibriJSON(archivioPath.toString());
        controller = new ControllerLibro(archivio);
        mainFrame = new MainFrame(controller);
    }

    @Test
    void testAvvioApp() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            MainFrame frame = new MainFrame(controller);

            Component queryBar = frame.getContentPane().getComponent(0);
            Assertions.assertTrue(queryBar instanceof QueryBarPanel);

            Component center = ((BorderLayout) frame.getContentPane().getLayout())
                    .getLayoutComponent(BorderLayout.CENTER);
            Assertions.assertTrue(center instanceof JScrollPane);

            JScrollPane scrollPane = (JScrollPane) center;
            Assertions.assertTrue(scrollPane.getViewport().getView() instanceof GridBoxLibroPanel);

            frame.dispose();
        });
    }

    @Test
    void testPerformanceMainFrameAvvio() throws InvocationTargetException, InterruptedException {
        long start = System.nanoTime();

        SwingUtilities.invokeAndWait(() -> {
            MainFrame frame = new MainFrame(controller);
            Assertions.assertNotNull(frame);
            frame.dispose();
        });

        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;

        System.out.println("Avvio GUI: " + durationMs + " ms");
        Assertions.assertTrue(durationMs < 1100, "La GUI si avvia lentamente");
    }

    @Test
    void stressTest() throws InterruptedException {
        int numeroLibri = 1000;

        for (int i = 0; i < numeroLibri; i++) {
            Libro libro = new Libro.LibroBuilder(
                    List.of(new Autore("Autore", "Numero" + i)),
                    "Titolo numero " + i,
                    "978000000" + String.format("%04d", i)
            ).build();
            controller.aggiungi(libro);
        }

        CountDownLatch latch = new CountDownLatch(1);

        long inizio = System.nanoTime();

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(controller);


            GridBoxLibroPanel gridPanel = (GridBoxLibroPanel)
                    ((JScrollPane) ((BorderLayout) frame.getContentPane().getLayout())
                            .getLayoutComponent(BorderLayout.CENTER))
                            .getViewport().getView();


            new Thread(() -> {
                while (true) {
                    if (gridPanel.getComponentCount() >= numeroLibri) {
                        latch.countDown();
                        break;
                    }
                    try {
                        Thread.sleep(50); // Evita busy waiting
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        });

        boolean completato = latch.await(10, TimeUnit.SECONDS);
        long fine = System.nanoTime();
        long tempoMs = (fine - inizio) / 1_000_000;

        Assertions.assertTrue(completato, "App impiega troppo tempo per visualizzare tutti i libri");
        System.out.println("Tempo caricamento + visualizzazazion " + numeroLibri + " libri: " + tempoMs + " ms");
        Assertions.assertTrue(tempoMs < 5000, "Visualizzazin lenta (>5s)");
    }

    @Test
    void testObserverAggiornamentoInTempoReale() throws InvocationTargetException, InterruptedException {
        GridBoxLibroPanel pannello = new GridBoxLibroPanel(controller, controller.cerca());

        // Registriamo l'observer manualmente
        controller.getArchivioLibri().aggiungiObserver(pannello);

        int libriIniziali = pannello.getComponentCount();

        Libro nuovo = new Libro.LibroBuilder(
                List.of(new Autore("Testxx", "Observerxx")),
                "Libro Observer",
                "9780000009999"
        ).build();

        controller.aggiungi(nuovo);

        SwingUtilities.invokeAndWait(() -> {
            int libriFinali = pannello.getComponentCount();
            Assertions.assertEquals(libriIniziali + 1, libriFinali, "Observer non aggiorna correttamente il pannello");
        });
    }

    @AfterAll
    void pulisciFile() throws IOException {
        if (Files.exists(archivioPath)) {
            Files.delete(archivioPath);
        }
    }
}

