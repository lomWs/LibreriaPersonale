package query.filtro;

import model.Libro;

import java.util.*;

public class CompositeFiltroArchivio implements FiltroArchivio {

    private List<FiltroArchivio> figli = new ArrayList<>();
    private String operazione; // AND or OR

    public CompositeFiltroArchivio(List<FiltroArchivio> figli,String operazione){
        this.figli=new ArrayList<>(figli);
    }
    public CompositeFiltroArchivio(){}

    @Override
    public boolean filtra(Libro l) {
        if(operazione=="AND")
            return figli.stream().allMatch(f -> f.filtra(l));//and tra tutti i filtri inseriti

        return figli.stream().anyMatch(f -> f.filtra(l));//or tra tutti i filtri inseriti
    }


    // i metodi di aggiunta e rimozione, non devono rendere lo stato dell'oggetto inconsistente
    public CompositeFiltroArchivio aggiungi(FiltroArchivio f){
        List<FiltroArchivio> nuoviFiltri = new ArrayList<>(this.figli);
        nuoviFiltri.add(f);
        return new CompositeFiltroArchivio(nuoviFiltri,this.operazione);
    }

    public CompositeFiltroArchivio rimuovi(FiltroArchivio f){
        List<FiltroArchivio> nuoviFiltri = new ArrayList<>(this.figli);
        nuoviFiltri.remove(f);
        return new CompositeFiltroArchivio(nuoviFiltri,this.operazione);
    }




    public List<FiltroArchivio> getFigli() {
        return new ArrayList<>(figli);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CompositeFiltroArchivio other)) return false;
        return new HashSet<>(this.figli).equals(new HashSet<>(other.figli));
        // set, ordine irrilevante;
    }


    public int hashCode() {
        return Objects.hash( new HashSet<>(this.figli));
    }


    public String toString() {
        return "Filtro composto: " + this.figli;
    }


}
