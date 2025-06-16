package main.storage;

import main.model.Libro;

import java.util.List;

public interface ArchivioLibri {


    public void inserisciLibro(Libro l);

    public void inserisciLibro(List<Libro> libri);

    public void eliminaLibro(String ISBN);

    public void eliminaLibro(List<String> ListaISBN);

    public List<Libro> visualizzaLibri();

    //da agiungere visualizzazione con fintri e ordinamento


}
