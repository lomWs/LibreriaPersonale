package query.filtro;

import model.Autore;
import model.Libro;

import java.util.Arrays;

public class FiltroPerAutore extends AbstractFiltroArchivio<String>{

    //private final String autore;

    public FiltroPerAutore(String autore){
        super(autore);
        //this.autore = autore;
    }


    @Override
    public boolean filtra(Libro l) {
        String[] sottoStringheAutore = super.parametroFiltro.toLowerCase().split(" ");
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
