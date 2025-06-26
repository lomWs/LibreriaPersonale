package archivio;

import model.Libro;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.util.List;

public class ArchivioLibriCSV implements ArchivioLibri{
    @Override
    public void inserisci(Libro l) {

    }

    @Override
    public void elimina(FiltroArchivio f) {

    }

    @Override
    public List<Libro> cerca(FiltroArchivio f, OrdinamentoArchivio o) {
        return List.of();
    }
}
