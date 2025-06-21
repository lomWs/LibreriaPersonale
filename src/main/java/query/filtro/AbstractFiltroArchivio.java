package query.filtro;

import model.Libro;

import java.util.Objects;

public abstract class AbstractFiltroArchivio implements FiltroArchivio {

    private String nomeFiltro;

    AbstractFiltroArchivio(String nomeFiltro){
        this.nomeFiltro = nomeFiltro;
    }



    @Override
    public abstract boolean filtra(Libro l);







    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFiltroArchivio that = (AbstractFiltroArchivio) o;
        return Objects.equals(nomeFiltro, that.nomeFiltro);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nomeFiltro);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
