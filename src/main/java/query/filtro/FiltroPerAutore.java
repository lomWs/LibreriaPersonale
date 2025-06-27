package query.filtro;

import model.Autore;
import model.Libro;

import java.util.Arrays;

public class FiltroPerAutore implements FiltroArchivio{

    private String autore;

    public FiltroPerAutore(String autore){
        super();
        this.autore = autore;
    }


    @Override
    public boolean filtra(Libro l) {
        String[] sottoStringheAutore = this.autore.toLowerCase().split(" ");
        for(Autore a : l.getAutori()){

            for (int i=0;i<sottoStringheAutore.length;i++){
                if(a.getNome().toLowerCase().contains(sottoStringheAutore[i])
                    || a.getCognome().toLowerCase().contains(sottoStringheAutore[i]) )
                    return true;

            }

        }
        return false;


    }
}
