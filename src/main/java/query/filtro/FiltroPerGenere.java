package query.filtro;

import model.GenereLibro;
import model.Libro;

public class FiltroPerGenere extends AbstractFiltroArchivio<GenereLibro> {


    public FiltroPerGenere(GenereLibro g){
        super(g);
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getGeneri().stream().anyMatch(g -> g.equals(this.parametroFiltro));
    }
}
