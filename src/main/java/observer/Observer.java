package observer;

import model.Libro;

import java.util.List;

public interface Observer {

    void aggiorna(List<Libro> libri);

}
