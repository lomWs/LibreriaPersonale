package query;

import archivio.ArchivioLibri;
import model.Libro;

import java.util.ArrayList;
import java.util.List;

public class QueryArchvioInserisci extends AbstractQueryArchivio{

   private List<Libro> libri;

    public  QueryArchvioInserisci(ArchivioLibri a,Libro l ){
        super(a);
        this.libri = new ArrayList<>();
        libri.add(l);
    }
    public  QueryArchvioInserisci(ArchivioLibri a, List<Libro> libri){
        super(a);
        this.libri = new ArrayList<>(libri);
    }

    @Override
    public void esegui() {
            archivio.inserisci(libri);

    }

}
