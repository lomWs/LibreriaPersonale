package query;

import archivio.ArchivioLibri;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

public class QueryArchivioCerca extends AbstractQueryArchivio {

    private String ISBN;
    private FiltroArchivio f;
    private OrdinamentoArchivio o;

    QueryArchivioCerca(ArchivioLibri a, String ISBN){
        super(a);
        this.ISBN=ISBN;
    }
    QueryArchivioCerca(ArchivioLibri a,FiltroArchivio f){
        super(a);
        this.f=f;
    }
    QueryArchivioCerca(ArchivioLibri a,FiltroArchivio f, OrdinamentoArchivio o){
        super(a);
        this.f=f;
        this.o=o;
    }
    QueryArchivioCerca(ArchivioLibri a,OrdinamentoArchivio o){
        super(a);
        this.o=o;
    }

    @Override
    public void esegui() {
        if(ISBN != null)
            archivio.cerca(this.ISBN);
        else
            archivio.cerca(this.f,this.o);

    }

}
