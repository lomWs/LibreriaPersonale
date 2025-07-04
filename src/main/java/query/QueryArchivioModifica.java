package query;

import archivio.ArchivioLibri;
import model.Libro;

import java.util.List;

public class QueryArchivioModifica extends AbstractQueryArchivio {

    private Libro l;
    QueryArchivioModifica(ArchivioLibri a, Libro libroModificato){
        super(a);
        this.l = libroModificato;

    }

    @Override
    public List<Libro>  esegui() {

        this.archivio.modifica(this.l);

        return null;
    }
}
