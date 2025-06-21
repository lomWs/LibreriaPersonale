package main.query.filtro;

import main.model.Libro;

import java.util.*;

public class CompositeFiltroArchivio implements FiltroArchivio {

    private List<FiltroArchivio> figli = new ArrayList<>();

    CompositeFiltroArchivio() {

    }

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



    public boolean equals(Object obj) {
        if (!(obj instanceof CompositeFiltroArchivio other)) return false;
        return new HashSet<>(this.figli).equals(new HashSet<>(other.figli));
        // usa set per ordine irrilevante;
    }


    public int hashCode() {
        return Objects.hash( new HashSet<>(this.figli));
    }


    public String toString() {
        return "Filtro composto: " + this.figli;
    }


}
