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

//        String[] sottoStringheTitoloInserito = this.parametroFiltro.split(" ");
//        String[] sottoStringheTitolo = l.getTitolo().split(" ");
//
//        if(sottoStringheTitolo.length - sottoStringheTitoloInserito.length  <= 1)
//            return false;
//
//
//
//        return Arrays.stream(sottoStringheTitoloInserito).anyMatch(l.getTitolo()::contains);


        String[] sottoStringheTitoloInput = this.parametroFiltro.split("\\s+");
        String[] sottoStringheTitolo = l.getTitolo().split("\\s+");

        // Ignora ricerche troppo brevi (es. 1 sola parola da 2 lettere)
        if (sottoStringheTitoloInput.length == 1 && sottoStringheTitoloInput[0].length() <= 2)
            return false;

        long paroleTrovate = Arrays.stream(sottoStringheTitoloInput)
                .filter(p -> Arrays.asList(sottoStringheTitolo).contains(p))
                .count();

        // Almeno il 60% delle parole devono essere presenti
        double percentualeMatch = (double) paroleTrovate / sottoStringheTitoloInput.length;
        return percentualeMatch >= 0.6;
    }
}
