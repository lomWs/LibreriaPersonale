package main.storage;

import main.model.Libro;
import main.query.QueryLibro;

import javax.management.Query;
import java.util.List;

public interface ArchivioLibri {


    public void inserisci(Libro l);

    public void inserisci(List<Libro> libri);

    public void elimina(String ISBN);

    public void elimina(List<String> ListaISBN);

    public void modifica(String ISBN,Libro libroModificato);

    public Libro cerca(String ISBN);

    //public List<Libro> visualizza();

    public List<Libro> cerca(QueryLibro q);




}
