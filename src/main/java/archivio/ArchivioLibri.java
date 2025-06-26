package archivio;

import model.Libro;
import query.QueryArchivioIF;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.util.List;

public interface ArchivioLibri {


    public void inserisci(Libro l);

    public default void inserisci(List<Libro> libri){
        for (Libro l: libri)
            inserisci(l);
    }

    public void elimina(FiltroArchivio f);

    public void elimina(String ISBN);

    public default void elimina(List<String> listaISBN){
        for(String s: listaISBN)
            elimina(s);
    }

    public void modifica(Libro libroModificato);

    public default void modifica(List<Libro> libriMod){
        for(Libro l: libriMod)
            modifica(l);
    }


    public Libro cerca(String ISBN);


    public List<Libro> cerca(FiltroArchivio f, OrdinamentoArchivio o);


}
