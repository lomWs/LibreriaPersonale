package query.filtro;

import model.GenereLibro;
import model.Libro;

public class FiltroPerGenere implements FiltroArchivio {
    private GenereLibro genere;

    public FiltroPerGenere(GenereLibro g){
        this.genere=g;
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getGeneri().stream().anyMatch(g -> g.equals(genere));
    }
}
