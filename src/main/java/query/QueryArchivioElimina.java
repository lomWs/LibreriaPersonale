package query;

import archivio.ArchivioLibri;
import query.filtro.FiltroArchivio;

import java.util.ArrayList;
import java.util.List;

public class QueryArchivioElimina extends AbstractQueryArchivio{

    private List<String> listaISBN;
    private FiltroArchivio f;



    QueryArchivioElimina(ArchivioLibri a, String ISBN) {
        super(a);
        listaISBN =new ArrayList<>();
        listaISBN.add(ISBN);

    }
    QueryArchivioElimina(ArchivioLibri a,List<String> isbn){
        super(a);
        listaISBN = new ArrayList<>(isbn);
    }
    QueryArchivioElimina(ArchivioLibri a,FiltroArchivio f){
        super(a);
        this.f=f;
    }


    @Override
    public void esegui() {
        if(listaISBN == null)
            archivio.elimina(f);
        else{
            archivio.elimina(listaISBN);

        }
    }
}
