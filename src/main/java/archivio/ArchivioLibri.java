package archivio;

import model.Libro;
import observer.Subject;
import query.QueryArchivioIF;
import query.filtro.FiltroArchivio;
import query.filtro.FiltroPerISBN;
import query.ordinamento.OrdinamentoArchivio;

import java.util.List;

public interface ArchivioLibri extends Subject {
    /**
    * ArchivioLibri è l'interfaccia che viene proposta come layer di più
    * basso livello per la gestione di un Archivio per salvare oggetti
    * di tipo Libro.
    * @See Subject
    *
    * */

    public void inserisci(Libro l) throws LibriPresentiException;

    public default void inserisci(List<Libro> libri)throws LibriPresentiException{
        for (Libro l: libri)
            inserisci(l);
    }

    public void elimina(FiltroArchivio f);

    public default void elimina(String ISBN){
        elimina(new FiltroPerISBN(ISBN));
    }

    public default void elimina(List<String> listaISBN){
        for(String s: listaISBN)
            elimina(s);
    }
    public default void modifica(Libro libroModificato){
        elimina(libroModificato.getISBN());
        inserisci(libroModificato);

    }
    public default void modifica(List<Libro> libriMod){
        for(Libro l: libriMod)
            modifica(l);
    }

    public List<Libro> cerca(FiltroArchivio f, OrdinamentoArchivio o);


}
