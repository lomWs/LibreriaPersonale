package archivio;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Libro;
import query.filtro.FiltroArchivio;
import query.ordinamento.OrdinamentoArchivio;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class ArchivioLibriJSON implements ArchivioLibri{

    private Gson gson;
    private final String percorsoFileDB;
    private final Path fileJson;


     public  ArchivioLibriJSON(String percorsoFileDB){
        this.percorsoFileDB = percorsoFileDB;
        this.fileJson = Path.of(percorsoFileDB);
        this.gson=new Gson();

    }


    @Override
    public void inserisci(Libro l) {
        try(FileWriter fw = new FileWriter(fileJson.toFile());) {
            this.gson.toJson(l,fw);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void inserisci(List<Libro> libri) {
        try(FileWriter fw = new FileWriter(fileJson.toFile());) {
            this.gson.toJson(libri,fw);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void elimina(FiltroArchivio f) {
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
                if (f!=null &&f.filtra(libro)) {
                    libri.add(libro);
                    first = false;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'eliminazione", e);
        }
        if(o != null)
            libri.sort(o);

        return libri;

    }






}
