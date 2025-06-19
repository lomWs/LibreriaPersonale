package main.query.filtro;

import main.model.Libro;
import main.model.StatoLibro;

public class FiltroPerStato implements FiltroArchivio{
    private StatoLibro stato;

    FiltroPerStato(StatoLibro s ){
        this.stato = s;
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getStato().equals(stato);
    }
}
