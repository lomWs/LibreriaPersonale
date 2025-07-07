package query;

import archivio.ArchivioLibri;
import model.Libro;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.util.List;

public class QueryArchivioCerca extends AbstractQueryArchivio {

    private final FiltroArchivio f;
    private final OrdinamentoArchivio o;

    public QueryArchivioCerca(ArchivioLibri a){
        super(a);
        this.f=null;
        this.o=null;
    }

    public QueryArchivioCerca(ArchivioLibri a,FiltroArchivio f){
        super(a);
        this.f=f;
        this.o=null;
    }
    public QueryArchivioCerca(ArchivioLibri a,FiltroArchivio f, OrdinamentoArchivio o){
        super(a);
        this.f=f;
        this.o=o;
    }
    public QueryArchivioCerca(ArchivioLibri a,OrdinamentoArchivio o){
        super(a);
        this.o=o;
        this.f=null;
    }

    @Override
    public List<Libro> esegui() {

        return archivio.cerca(this.f, this.o);
    }

}
