package query;

import archivio.ArchivioLibri;
import query.filtro.CompositeFiltroArchivio;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractQueryArchivio implements QueryArchivioIF {
    protected final ArchivioLibri archivio;

    AbstractQueryArchivio(ArchivioLibri a){
        this.archivio = a;
    }

    public abstract void esegui();

}
