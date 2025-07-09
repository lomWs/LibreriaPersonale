package archivio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Libro;
import observer.Observer;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class ArchivioLibriJSON implements ArchivioLibri{

    /**
    * La classe ArchivioLibriJSON implementa l'interfaccia ArchivioLibri, fa uso della classe
    * Gson per gestire l'accesso ai file JSon e di una variabie di tipo Strin e Path per mantenre  la locazione
    * del file contente l'archivio. Utilizza inoltre una lista di observer per tenere traccia degli Observer
    * che si registrano
    *
    * @See Gson
    * @See ArchivioLibri
    * */



    private final Gson gson;
    private final String percorsoFileDB;
    private final Path fileJson;
    private final List<Observer> observers = new ArrayList<>();


     public  ArchivioLibriJSON(String percorsoFileDB){
        this.percorsoFileDB = percorsoFileDB;
        this.controllaOCreaFileJson();
        this.fileJson = Path.of(percorsoFileDB);
        this.gson=new GsonBuilder()
                .setPrettyPrinting()
                .create();

    }

    public  ArchivioLibriJSON(String percorsoFileDB,Gson gson){
        this.percorsoFileDB = percorsoFileDB;
        this.controllaOCreaFileJson();
        this.fileJson = Path.of(percorsoFileDB);
        this.gson=gson;

    }


    @Override
    public void inserisci(Libro l) throws LibriPresentiException{
        List<Libro> libriGiaPresenti = cerca(null,null);
        if(libriGiaPresenti.contains(l))
            throw new LibriPresentiException();
        libriGiaPresenti.add(l);
        try(FileWriter fw = new FileWriter(fileJson.toFile());) {
            this.gson.toJson(libriGiaPresenti,fw);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notifica(libriGiaPresenti);

    }

    @Override
    public void inserisci(List<Libro> libri) throws LibriPresentiException {
         List<Libro> libriGiaPresenti = cerca(null,null);
        if(libri.stream().anyMatch(libriGiaPresenti::contains))
            throw new LibriPresentiException("Almeno un libro con lo stesso ISBN è già stato inserito");

        libriGiaPresenti.addAll(libri);
        try(FileWriter fw = new FileWriter(fileJson.toFile());) {
            this.gson.toJson(libriGiaPresenti,fw);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notifica(libriGiaPresenti);
    }


    @Override
    public void elimina(FiltroArchivio f){
         /*
         * Funzione che elimina i libri secondo le condizioni imposte dal @param f. La libreria Gson non permette
         * di accedere tramite indice al file dunque per l'eliminazione si ricercano gli  oggetti da eliminare
         * e si crea un file temporaneo senza quegli oggetti il quale successivamente verrà sovrascritto sul vecchio
         * file dell'archvio
         *
         * */



        Path temp = Path.of(fileJson.toString() + ".tmp");


        try (
                JsonReader reader = new JsonReader(new FileReader(fileJson.toFile()));
                BufferedWriter writer = Files.newBufferedWriter(temp)
        ) {
            writer.write("[\n");
            reader.beginArray();
            boolean first = true;

            while (reader.hasNext()) {
                Libro libro = gson.fromJson(reader, Libro.class);
                if (!f.filtra(libro)) {
                    if (!first) writer.write(",");
                    writer.write(gson.toJson(libro));
                    first = false;
                }
            }

            reader.endArray();
            writer.write("]\n");
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'eliminazione", e);
        }

        try {
            Files.move(temp, fileJson, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel sovrascrivere il file JSON", e);
        }

        notifica(null);
    }




    @Override
    public List<Libro> cerca(FiltroArchivio f, OrdinamentoArchivio o) {


        List<Libro> libri = new ArrayList<>();

        try (
                JsonReader reader = new JsonReader(new FileReader(fileJson.toFile()));
        ) {
            reader.beginArray();
            boolean first = true;

            while (reader.hasNext()) {
                Libro libro = gson.fromJson(reader, Libro.class);
                if (f==null || f.filtra(libro)) {
                    libri.add(libro);
                    first = false;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Errore durante la ricerca", e);
        }
        if(o != null)
            libri.sort(o);

        notifica(libri);

        return libri;

    }


    /**
    * Metodi @Override che implementano l'uso del design pattern Observer modificato, la funzione notifica infatti
    * ha come @param libri che permette di notificare direttamente il cambiamento agli observer senza dover rieffettuare
    * una richiesta di lettura dell'archivio
    *
    * @See Subject
    * */

    @Override
    public void aggiungiObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void rimuoviObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifica(List<Libro> libri) {
        for (Observer o : observers) {
            o.aggiorna(libri);
        }

    }


    private  void controllaOCreaFileJson() {
         /*
         * Funzione di utility privata che gestisce il caso in cui non vi sia il file contente l'archivio. La funzione
         * se non trova il file corrtto ne crea uno vuoto
         *
         * */



        File file = new File(percorsoFileDB);

        if (!file.exists()) {
            try {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

}
