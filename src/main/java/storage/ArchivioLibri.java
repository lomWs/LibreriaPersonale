package storage;

import model.Libro;
import query.QueryLibro;

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
