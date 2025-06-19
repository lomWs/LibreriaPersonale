package main.query.filtro;

import main.model.Libro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CompositeFiltroArchivio implements FiltroArchivio {

    private List<FiltroArchivio> figli = new ArrayList<>();

    @Override
    public boolean filtra(Libro l) {
        return figli.stream().allMatch(f -> f.filtra(l));//and tra tutti i filtri inseriti
    }


    public void aggiungi(FiltroArchivio f){
        if (!figli.contains(f))
            figli.add(f);
    }

    public void rimuovi(FiltroArchivio f){
        if (!figli.contains(f))
            figli.add(f);
    }
}
