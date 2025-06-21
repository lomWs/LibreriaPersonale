package query.filtro;

import model.Libro;
import model.StatoLibro;

public class FiltroPerStato implements FiltroArchivio {
    private StatoLibro stato;

    FiltroPerStato(StatoLibro s ){
        this.stato = s;
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getStato().equals(stato);
    }
}
