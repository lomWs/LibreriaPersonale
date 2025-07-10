package query;

import model.Libro;

import java.util.List;

public interface QueryArchivioIF {

    /**
     * Utilizzo del design pattern Command semplificato per incapsluare i comandi
     * */

    public List<Libro> esegui();
}
