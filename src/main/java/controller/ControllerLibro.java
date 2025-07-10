package controller;

import archivio.ArchivioLibri;
import model.Libro;
import query.*;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.util.List;


public class ControllerLibro {

    /**
     * La classe ControllerLibro permette l'utilizzo e l'esecuzione di Query piu o meno complesse senza dover mantenere
     * riferimenti all' ArchivioLibri. Essa è utilizzata come layer di separazione agiguntivo tra il backend ed il
     * fronteend
     *
     * @See ArchivioLibri
     * @See QueryArchivioIF
     */




    private final ArchivioLibri archivioLibri;

    public ControllerLibro(ArchivioLibri archivioLibri) {
        this.archivioLibri = archivioLibri;
    }

    public ArchivioLibri getArchivioLibri(){return this.archivioLibri;}


    public List<Libro> aggiungi(Libro libro) {
        QueryArchivioIF queryAggiungi = new QueryArchvioInserisci(archivioLibri,libro);
        return queryAggiungi.esegui();
    }

    public List<Libro> aggiungi(List<Libro> libri) {
        QueryArchivioIF queryAggiungi = new QueryArchvioInserisci(archivioLibri,libri);
        return queryAggiungi.esegui();
    }


    public List<Libro> cerca() {
        QueryArchivioIF queryCerca = new QueryArchivioCerca(archivioLibri);
        return queryCerca.esegui();
    }
    public List<Libro> cerca(FiltroArchivio filtro) {
        QueryArchivioIF queryCerca = new QueryArchivioCerca(archivioLibri,filtro);
        return queryCerca.esegui();
    }
    public List<Libro> cerca(FiltroArchivio filtro, OrdinamentoArchivio ordinamento) {
        QueryArchivioIF queryCerca = new QueryArchivioCerca(archivioLibri,filtro,ordinamento);
        return queryCerca.esegui();
    }
    public List<Libro> cerca(OrdinamentoArchivio ordinamento) {
        QueryArchivioIF queryCerca = new QueryArchivioCerca(archivioLibri,ordinamento);
        return queryCerca.esegui();
    }


    public List<Libro> elimina(String isbn) {
        QueryArchivioIF queryElimina = new QueryArchivioElimina(archivioLibri,isbn);
        return queryElimina.esegui();
    }
    public List<Libro> elimina(List<String> isbn) {
        QueryArchivioIF queryElimina = new QueryArchivioElimina(archivioLibri,isbn);
        return queryElimina.esegui();
    }
    public List<Libro> elimina(FiltroArchivio filtro) {
        QueryArchivioIF queryElimina = new QueryArchivioElimina(archivioLibri,filtro);
        return queryElimina.esegui();

    }

    public List<Libro> modifica(Libro libro) {
        QueryArchivioIF queryModifica = new QueryArchivioModifica(archivioLibri,libro);
        return queryModifica.esegui();
    }

}