package query;

import archivio.ArchivioLibri;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

public class QueryArchivioCerca extends AbstractQueryArchivio {

    private FiltroArchivio f;
    private OrdinamentoArchivio o;


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
    public void esegui() {
        //print momentanea da rimuovere
        System.out.println(
            archivio.cerca(this.f,this.o));

    }

}
