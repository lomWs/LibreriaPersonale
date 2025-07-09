package observer;

import model.Libro;

import java.util.List;

public interface Subject {
     void aggiungiObserver(Observer o);

     void rimuoviObserver(Observer o);

     void notifica(List<Libro> libri);
}
