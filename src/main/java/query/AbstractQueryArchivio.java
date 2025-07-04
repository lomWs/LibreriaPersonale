package query;

import archivio.ArchivioLibri;
import model.Libro;

import java.util.List;

public abstract class AbstractQueryArchivio implements QueryArchivioIF {
    protected final ArchivioLibri archivio;

    AbstractQueryArchivio(ArchivioLibri a){
        this.archivio = a;
    }

    public abstract List<Libro> esegui();


}
