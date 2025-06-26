package query;

import archivio.ArchivioLibri;
import model.Libro;

public class QueryArchivioModifica extends AbstractQueryArchivio {

    private Libro l;
    QueryArchivioModifica(ArchivioLibri a, Libro libroModificato){
        super(a);
        this.l = libroModificato;

    }

    @Override
    public void esegui() {

        this.archivio.modifica(this.l);

    }
}
