package query.filtro;

import model.Libro;

import java.util.Arrays;

public class FiltroPerTitolo extends AbstractFiltroArchivio<String>{


    public FiltroPerTitolo(String titolo){super(titolo);}

    @Override
    public boolean filtra(Libro l) {
        if(this.parametroFiltro.equalsIgnoreCase(l.getTitolo()))
            return  true;

        String[] sottoStringheTitoloInput = this.parametroFiltro.split("\\s+");
        String[] sottoStringheTitolo = l.getTitolo().split("\\s+");

        // ritorno false per le ricerche troppo brevi
        if (sottoStringheTitoloInput.length == 1 && sottoStringheTitoloInput[0].length() <= 2)
            return false;

        long paroleTrovate = Arrays.stream(sottoStringheTitoloInput)
                .filter(p -> Arrays.asList(sottoStringheTitolo).contains(p))
                .count();

        // accetto i risulati che abbiano almeno il 60% di similarità
        double percentualeMatch = (double) paroleTrovate / sottoStringheTitoloInput.length;
        return percentualeMatch >= 0.6;
    }
}
