package main.storage;

import main.model.Libro;

import java.util.List;

public interface ArchivioLibri {


    public void inserisci(Libro l);

    public void inserisci(List<Libro> libri);

    public void elimina(String ISBN);

    public void elimina(List<String> ListaISBN);

    public List<Libro> visualizza();

    //da agiungere visualizzazione con fintri e ordinamento


}
