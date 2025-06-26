package query.filtro;

import model.GenereLibro;
import model.Libro;

public class FiltroPerISBN implements FiltroArchivio{

    private String ISBN;

    public FiltroPerISBN(String ISBN){
        this.ISBN=ISBN;
    }


    @Override
    public boolean filtra(Libro l) {
        return l.getISBN().equals(this.ISBN);
    }


}
