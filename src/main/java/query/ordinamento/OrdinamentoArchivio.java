package query.ordinamento;

import model.Libro;

import java.util.Comparator;

public interface OrdinamentoArchivio extends Comparator<Libro> {
    @Override
    public int compare(Libro a, Libro b);
}
