package main.query.ordinamento;

import main.model.Libro;

import java.util.function.Function;

public class OrdinamentoPerAttributo<T extends Comparable<T>> implements OrdinamentoArchivio {
    //nel caso dell'ordinamento gestisco in modo diverso poiche facilmente standardizzabile(cresc,decres)

    private  Function<Libro, T> estrattore;//gestione univoca di attributi diversi
    private  boolean crescente;

    public OrdinamentoPerAttributo(Function<Libro, T> estrattore, boolean crescente) {
        this.estrattore = estrattore;
        this.crescente = crescente;

    }

    @Override
    public int compare(Libro a, Libro b) {
        T valA = estrattore.apply(a);
        T valB = estrattore.apply(b);
        int result = valA.compareTo(valB);
        return crescente ? result : -result;
    }


}
