package main.storage;

import main.model.Libro;

import java.util.List;

public interface ArchivioLibri {


    public void inserisciLibro(Libro l);

    public void eliminaLibro(String ISBN);

    public List<Libro> visualizzaLibri();

    //da agiungere visualizzazione con fintri e ordinamento


}
