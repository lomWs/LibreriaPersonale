package query;

import archivio.ArchivioLibri;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

public class QueryArchivioCerca extends AbstractQueryArchivio {

    private FiltroArchivio f;
    private OrdinamentoArchivio o;


    QueryArchivioCerca(ArchivioLibri a,FiltroArchivio f){
        super(a);
        this.f=f;
        this.o=null;
    }
    QueryArchivioCerca(ArchivioLibri a,FiltroArchivio f, OrdinamentoArchivio o){
        super(a);
        this.f=f;
        this.o=o;
    }
    QueryArchivioCerca(ArchivioLibri a,OrdinamentoArchivio o){
        super(a);
        this.o=o;
        this.f=null;
    }

    @Override
    public void esegui() {
            archivio.cerca(this.f,this.o);

    }

}
