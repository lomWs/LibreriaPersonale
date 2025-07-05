package query.filtro;

import model.Libro;

import java.util.Arrays;

public class FiltroPerTitolo extends AbstractFiltroArchivio<String>{

    //private String titolo;

    public FiltroPerTitolo(String titolo){super(titolo);}

    @Override
    public boolean filtra(Libro l) {
        if(this.parametroFiltro.equalsIgnoreCase(l.getTitolo()))
            return  true;

        String[] sottoStringheTitolo = this.parametroFiltro.split(" ");

        return Arrays.stream(sottoStringheTitolo).anyMatch(l.getTitolo()::contains);
    }
}
