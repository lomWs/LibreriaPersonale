package main.query.filtro;

import main.model.GenereLibro;
import main.model.Libro;

public class FiltroPerGenere implements FiltroArchivio{
    private GenereLibro genere;

    FiltroPerGenere(GenereLibro g){
        this.genere=g;
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getGeneri().stream().anyMatch(g -> g.equals(genere));
    }
}
