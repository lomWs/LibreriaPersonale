package query.filtro;

import model.Libro;

import java.util.Arrays;

public class FiltroPerTitolo implements FiltroArchivio{

    private String titolo;

    public FiltroPerTitolo(String titolo){this.titolo =titolo;}

    @Override
    public boolean filtra(Libro l) {
        if(this.titolo.equalsIgnoreCase(l.getTitolo()))
            return  true;

        String[] sottoStringheTitolo = this.titolo.split(" ");

        return Arrays.stream(sottoStringheTitolo).anyMatch(l.getTitolo()::contains);
    }
}
