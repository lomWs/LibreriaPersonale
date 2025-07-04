package query;

import archivio.ArchivioLibri;
import model.Libro;
import query.filtro.FiltroArchivio;

import java.util.ArrayList;
import java.util.List;

public class QueryArchivioElimina extends AbstractQueryArchivio{

    private List<String> listaISBN;
    private FiltroArchivio f;



    public QueryArchivioElimina(ArchivioLibri a, String ISBN) {
        super(a);
        listaISBN =new ArrayList<>();
        listaISBN.add(ISBN);

    }
    public  QueryArchivioElimina(ArchivioLibri a,List<String> isbn){
        super(a);
        listaISBN = new ArrayList<>(isbn);
    }
    public QueryArchivioElimina(ArchivioLibri a,FiltroArchivio f){
        super(a);
        this.f=f;
    }


    @Override
    public List<Libro> esegui() {
        if(listaISBN == null)
            archivio.elimina(f);
        else{
            archivio.elimina(listaISBN);

        }
        return null;
    }
}
