import archivio.LibriPresentiException;
import model.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import archivio.ArchivioLibri;
import archivio.ArchivioLibriJSON;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import query.*;
import query.filtro.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryTest {


    private ArchivioLibri archivio;

    @BeforeAll
     void setUp(){
        Path path = Paths.get("src/test/archivioTest.json");
        this.archivio =new ArchivioLibriJSON("src/test/archivioTest.json");
        inserimentoLibriIniziale();
    }

    private void inserimentoLibriIniziale(){
        Autore eco = new Autore("Umberto", "Eco");
        Autore manzoni = new Autore("Alessandro", "Manzoni");
        Autore levi = new Autore("Primo", "Levi");
        Autore ferrante = new Autore("Elena", "Ferrante");
        Autore ammaniti = new Autore("Niccolò", "Ammaniti");
        Autore deledda = new Autore("Grazia", "Deledda");
        Autore baricco = new Autore("Alessandro", "Baricco");
        Autore camilleri = new Autore("Andrea", "Camilleri");
        Autore saviano = new Autore("Roberto", "Saviano");
        Autore carlo = new Autore("Carlo", "Lucarelli");
        Autore michele = new Autore("Michele", "Serra");


        List<Libro> libri = List.of(
                new Libro.LibroBuilder(List.of(eco), "Il nome della rosa", "9788806170343")
                        .generi(Set.of(GenereLibro.STORICO, GenereLibro.GIALLO))
                        .stato(StatoLibro.DA_LEGGERE)
                        .valutazione(ValutazioneLibro.OTTIMO)
                        .build(),

                new Libro.LibroBuilder(List.of(eco), "Il pendolo di Foucault", "9788806173771")
                        .generi(Set.of(GenereLibro.FILOSOFICO, GenereLibro.THRILLER))
                        .stato(StatoLibro.DA_LEGGERE)
                        .valutazione(ValutazioneLibro.BUONO)
                        .build(),

                new Libro.LibroBuilder(List.of(manzoni), "I Promessi Sposi", "9788807900000")
                        .generi(Set.of(GenereLibro.STORICO))
                        .stato(StatoLibro.DA_LEGGERE)
                        .valutazione(ValutazioneLibro.ECCELLENTE)
                        .build(),

                new Libro.LibroBuilder(List.of(manzoni), "La colonna infame", "9788807900001")
                        .generi(Set.of(GenereLibro.SAGGIO))
                        .build(),

                new Libro.LibroBuilder(List.of(levi), "Se questo è un uomo", "9788806181547")
                        .generi(Set.of(GenereLibro.STORICO))
                        .stato(StatoLibro.DA_LEGGERE)
                        .valutazione(ValutazioneLibro.BUONO)
                        .build(),

                new Libro.LibroBuilder(List.of(levi), "La tregua", "9788806181554")
                        .generi(Set.of(GenereLibro.AUTOBIOGRAFICO))
                        .stato(StatoLibro.LETTO)
                        .build(),

                new Libro.LibroBuilder(List.of(ferrante), "L'amica geniale", "9788806214108")
                        .generi(Set.of(GenereLibro.ROMANZO))
                        .valutazione(ValutazioneLibro.ECCELLENTE)
                        .build(),

                new Libro.LibroBuilder(List.of(ferrante), "Storia del nuovo cognome", "9788806225753")
                        .generi(Set.of(GenereLibro.ROMANZO))
                        .build(),

                new Libro.LibroBuilder(List.of(ammaniti), "Io non ho paura", "9788806202341")
                        .generi(Set.of(GenereLibro.THRILLER))
                        .stato(StatoLibro.LETTO)
                        .valutazione(ValutazioneLibro.ECCELLENTE)
                        .build(),

                new Libro.LibroBuilder(List.of(deledda), "Canne al vento", "9788807700001")
                        .generi(Set.of(GenereLibro.ROMANZO, GenereLibro.SOCIALE))
                        .build(),


                new Libro.LibroBuilder(List.of(saviano, carlo), "La paranza dei bambini - indagine congiunta", "9788806234567")
                        .generi(Set.of(GenereLibro.THRILLER))
                        .build(),

                new Libro.LibroBuilder(List.of(camilleri, baricco), "Conversazioni sul tempo", "9788806237890")
                        .generi(Set.of(GenereLibro.SAGGIO, GenereLibro.FILOSOFICO))
                        .build(),


                new Libro.LibroBuilder(List.of(michele, saviano), "Il peso della libertà", "9788806240004")
                        .generi(Set.of(GenereLibro.EDUCAZIONE, GenereLibro.SAGGIO))
                        .build()
        );

        QueryArchivioIF q1 = new QueryArchvioInserisci(this.archivio,libri);
        q1.esegui();
    }


    @Test
    public void inserisciEdElimina(){
        Autore orwell = new Autore("George", "Orwell");
        Autore austen = new Autore("Jane", "Austen");
        Autore marquez = new Autore("Gabriel", "García Márquez");
        Autore murakami = new Autore("Haruki", "Murakami");
        Autore rowling = new Autore("J.K.", "Rowling");

        List<Libro> nuoviLibri = List.of(
                new Libro.LibroBuilder(List.of(orwell), "1984", "9780451524935")
                        .build(),

                new Libro.LibroBuilder(List.of(austen), "Orgoglio e pregiudizio", "9780141439518")
                        .generi(Set.of(GenereLibro.ROMANZO, GenereLibro.NARRATIVA))
                        .stato(StatoLibro.LETTO)
                        .valutazione(ValutazioneLibro.OTTIMO)
                        .build(),

                new Libro.LibroBuilder(List.of(marquez), "Cent'anni di solitudine", "9780060883287")
                        .generi(Set.of(GenereLibro.FANTASY, GenereLibro.ROMANZO))
                        .stato(StatoLibro.DA_LEGGERE)

                        .build(),

                new Libro.LibroBuilder(List.of(murakami), "Norwegian Wood", "9780375704024")
                        .generi(Set.of(GenereLibro.ROMANZO, GenereLibro.DRAMMATICO))
                        .stato(StatoLibro.LETTO)
                        .valutazione(ValutazioneLibro.BUONO)
                        .build(),

                new Libro.LibroBuilder(List.of(rowling), "Harry Potter e la pietra filosofale", "9780747532699")
                        .generi(Set.of(GenereLibro.FANTASY, GenereLibro.AVVENTURA))

                        .valutazione(ValutazioneLibro.ECCELLENTE)
                        .build()

        );

        QueryArchivioIF q1 = new QueryArchvioInserisci(this.archivio,nuoviLibri);
        q1.esegui();


        List<FiltroArchivio> filtri = nuoviLibri.stream()
                .map(libro -> new FiltroPerISBN(libro.getISBN()))
                .collect(Collectors.toList());

        FiltroArchivio filtroComposto = new CompositeFiltroArchivio(filtri,"OR");

        QueryArchivioIF q3= new QueryArchivioCerca(this.archivio,filtroComposto);
        List<Libro> ris = q3.esegui();



        Assertions.assertTrue(ris.containsAll(nuoviLibri));

        List<String> isbns = nuoviLibri.stream()
                .map(Libro::getISBN)
                .collect(Collectors.toList());

        QueryArchivioIF q4 = new QueryArchivioElimina(this.archivio,isbns);
        q4.esegui();


        Assertions.assertTrue(q3.esegui().isEmpty(), "Nessun libro deve essere presente dopo l'eliminazione");

    }


    @Test
    void testLibriPresentiException() {
        Autore eco = new Autore("Umberto", "Eco");
        Libro libroGiaPresente= new Libro.LibroBuilder(List.of(eco), "Il pendolo di Foucault", "9788806173771")
                .generi(Set.of(GenereLibro.FILOSOFICO, GenereLibro.THRILLER))
                .stato(StatoLibro.DA_LEGGERE)
                .valutazione(ValutazioneLibro.BUONO)
                .build();

        Assertions.assertThrows(LibriPresentiException.class, () -> {
            QueryArchivioIF q1 = new QueryArchvioInserisci(this.archivio,libroGiaPresente);
            q1.esegui();
        });
    }



    static Stream<Arguments> filtriCombinatiProvider() {
        return Stream.of(
                Arguments.of(
                        List.of(new FiltroPerTitolo("Il nome della rosa")),
                        "AND",
                        Set.of("Il nome della rosa")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("Il nome della rosa"),
                                new FiltroPerAutore("Umberto")
                        ),
                        "AND",
                        Set.of("Il nome della rosa")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("Il nome della rosa"),
                                new FiltroPerAutore("Lucarelli")
                        ),
                        "OR",
                        Set.of("Il nome della rosa", "La paranza dei bambini - indagine congiunta")
                ),
                Arguments.of(
                        List.of(new FiltroPerValutazione(ValutazioneLibro.ECCELLENTE)),
                        "AND",
                        Set.of("I Promessi Sposi", "L'amica geniale", "Io non ho paura")
                ),
                Arguments.of(
                        List.of(new FiltroPerStato(StatoLibro.LETTO)),
                        "AND",
                        Set.of("La tregua", "Io non ho paura")
                ),
                Arguments.of(
                        List.of(new FiltroPerValutazione(ValutazioneLibro.ECCELLENTE)),
                        "AND",
                        Set.of("I Promessi Sposi", "L'amica geniale", "Io non ho paura")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerStato(StatoLibro.DA_LEGGERE),
                                new FiltroPerValutazione(ValutazioneLibro.OTTIMO)
                        ),
                        "AND",
                        Set.of("Il nome della rosa")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerStato(StatoLibro.LETTO),
                                new FiltroPerValutazione(ValutazioneLibro.BUONO)
                        ),
                        "OR",
                        Set.of("La tregua", "Io non ho paura", "Il pendolo di Foucault", "Se questo è un uomo")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerStato(StatoLibro.DA_LEGGERE),
                                new FiltroPerValutazione(ValutazioneLibro.BUONO)
                        ),
                        "AND",
                        Set.of("Il pendolo di Foucault", "Se questo è un uomo")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerValutazione(ValutazioneLibro.ECCELLENTE),
                                new FiltroPerValutazione(ValutazioneLibro.OTTIMO)
                        ),
                        "OR",
                        Set.of("Il nome della rosa", "I Promessi Sposi", "L'amica geniale", "Io non ho paura")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerStato(StatoLibro.LETTO),
                                new FiltroPerValutazione(ValutazioneLibro.ECCELLENTE)
                        ),
                        "AND",
                        Set.of("Io non ho paura")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerStato(StatoLibro.LETTO),
                                new FiltroPerValutazione(ValutazioneLibro.OTTIMO)
                        ),
                        "AND",
                        Set.of()
                ),
                Arguments.of(
                        List.of(new FiltroPerTitolo("Il pendolo di Foucault")),
                        "AND",
                        Set.of("Il pendolo di Foucault")
                ),
                Arguments.of(
                        List.of(new FiltroPerISBN("9788806170343")),
                        "AND",
                        Set.of("Il nome della rosa")
                ),
                Arguments.of(
                        List.of(new FiltroPerISBN("9788806214108")),
                        "AND",
                        Set.of("L'amica geniale")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("Il nome della rosa"),
                                new FiltroPerTitolo("Il pendolo di Foucault")
                        ),
                        "OR",
                        Set.of("Il nome della rosa", "Il pendolo di Foucault")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("Il nome della rosa"),
                                new FiltroPerISBN("9788806173771")
                        ),
                        "OR",
                        Set.of("Il nome della rosa", "Il pendolo di Foucault")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("La tregua"),
                                new FiltroPerISBN("9788806181554")
                        ),
                        "AND",
                        Set.of("La tregua")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("La tregua"),
                                new FiltroPerISBN("9788806170343")
                        ),
                        "AND",
                        Set.of()
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerTitolo("Il pendolo di Foucault"),
                                new FiltroPerAutore("Umberto"),
                                new FiltroPerStato(StatoLibro.DA_LEGGERE)
                        ),
                        "AND",
                        Set.of("Il pendolo di Foucault")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerValutazione(ValutazioneLibro.ECCELLENTE),
                                new FiltroPerAutore("Ferrante")
                        ),
                        "AND",
                        Set.of("L'amica geniale")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerGenere(GenereLibro.ROMANZO),
                                new FiltroPerStato(StatoLibro.NULL)
                        ),
                        "AND",
                        Set.of("Canne al vento", "Storia del nuovo cognome","L'amica geniale")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerAutore("Saviano"),
                                new FiltroPerTitolo("peso")
                        ),
                        "OR",
                        Set.of("La paranza dei bambini - indagine congiunta", "Il peso della libertà")
                ),
                Arguments.of(
                        List.of(
                                new FiltroPerStato(StatoLibro.DA_LEGGERE),
                                new FiltroPerValutazione(ValutazioneLibro.BUONO)
                        ),
                        "AND",
                        Set.of("Il pendolo di Foucault", "Se questo è un uomo")
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filtriCombinatiProvider")
    void testCombinazioniDiFiltri(List<FiltroArchivio> filtri, String operatore, Set<String> titoliAttesi) {
        CompositeFiltroArchivio filtroComposto = new CompositeFiltroArchivio(filtri, operatore);
        QueryArchivioIF query = new QueryArchivioCerca(archivio, filtroComposto);
        List<Libro> risultati = query.esegui();

        Set<String> titoliRisultato = risultati.stream()
                .map(Libro::getTitolo)
                .collect(Collectors.toSet());

        Assertions.assertEquals(titoliAttesi, titoliRisultato);
    }



    @Test
    public void modifica(){
        //eseguo una query di ricerca per prendermi il libro e modificarlo:

        QueryArchivioIF q1 = new QueryArchivioCerca(this.archivio,new FiltroPerISBN("9788806173771"));
        Libro libroDaModificare=q1.esegui().getFirst();

        libroDaModificare.setStato(StatoLibro.LETTO);
        libroDaModificare.setValutazione(ValutazioneLibro.INSUFFICIENTE);

        QueryArchivioIF q2 = new QueryArchivioModifica(this.archivio,libroDaModificare);
        q2.esegui();

        Libro libroModificato = q1.esegui().getFirst();

        Assertions.assertTrue(libroModificato.getStato().equals(libroDaModificare.getStato())
                                && libroModificato.getValutazione().equals(libroDaModificare.getValutazione()));

    }


    @AfterAll
    static void eliminaTutto() throws IOException {
        Path path = Paths.get("src/test/archivioTest.json");
        if (Files.exists(path)) {
            Files.delete(path);

        }
    }
}
