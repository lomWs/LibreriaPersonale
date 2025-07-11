package query.filtro;

import model.Libro;
import model.StatoLibro;

public class FiltroPerStato extends AbstractFiltroArchivio<StatoLibro> {



    public FiltroPerStato(StatoLibro s ){
        super(s);
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getStato().equals(this.parametroFiltro);
    }
}
