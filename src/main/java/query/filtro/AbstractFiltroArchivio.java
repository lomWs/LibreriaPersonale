package query.filtro;

import model.Libro;

import java.util.Objects;

public abstract class AbstractFiltroArchivio<T> implements FiltroArchivio {

    protected final T parametroFiltro;

    AbstractFiltroArchivio(T parametroFiltro){
        this.parametroFiltro = parametroFiltro;
    }

    @Override
    public abstract boolean filtra(Libro l);


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFiltroArchivio<T> that = (AbstractFiltroArchivio<T>) o;
        return Objects.equals(parametroFiltro, that.parametroFiltro);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(parametroFiltro);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
