package query.filtro;

import model.GenereLibro;
import model.Libro;

public class FiltroPerISBN extends AbstractFiltroArchivio<String>{



    public FiltroPerISBN(String ISBN){super(ISBN);}


    @Override
    public boolean filtra(Libro l) {
        return l.getISBN().equals(super.parametroFiltro);
    }


}
