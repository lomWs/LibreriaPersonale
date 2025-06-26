package query.ordinamento;

import model.Libro;

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
        T valA = this.estrattore.apply(a);
        T valB = this.estrattore.apply(b);
        int result = valA.compareTo(valB);
        return this.crescente ? result : -result;
    }


}
