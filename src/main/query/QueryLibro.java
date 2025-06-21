package main.query;

import main.query.filtro.FiltroArchivio;
import main.query.ordinamento.OrdinamentoArchivio;

import java.util.ArrayList;
import java.util.List;

public class QueryLibro {
    private List<FiltroArchivio> filtri = new ArrayList<>();
    private List<OrdinamentoArchivio> ordinamenti = new ArrayList<>();


    public void aggiungi(FiltroArchivio filtro){
        if (!filtri.contains(filtro))
            filtri.add(filtro);
    }
    public void aggiungi(OrdinamentoArchivio ordinamento){
        if (ordinamenti.contains(ordinamento))
            ordinamenti.add(ordinamento);
    }

    public void rimuovi(FiltroArchivio filtro){
        if (filtri.contains(filtro))
            filtri.remove(filtro);
    }
    public void rimuovi(OrdinamentoArchivio ordinamento){
        if (ordinamenti.contains(ordinamento))
            ordinamenti.remove(ordinamento);
    }

}
