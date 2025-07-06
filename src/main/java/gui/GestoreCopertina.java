package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;


public class GestoreCopertina {


    public static ImageIcon loadIcon(String imagePath, int width, int height) {
        if(imagePath.equals(" "))
            return null;
        try {
            // Carica l'immagine dal percorso
            Image image = ImageIO.read(new File(imagePath));

            // Ridimensiona l'immagine
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Restituisci un'ImageIcon con l'immagine ridimensionata
            return new ImageIcon(scaledImage);

        } catch (Exception e) {
            // Gestisce eventuali eccezioni (file non trovato, formato immagine errato, ecc.)
            System.err.println("Errore nel caricare l'immagine: " + e.getMessage());
            return null;
        }
    }

        // Metodo che copia e rinomina l'immagine usando l'ISBN del libro
    public static String salvataggioCopertina(File sourceFile) {
            // check per vedere se il file inserito è un immagine
            if (!estensioneValida(sourceFile)) {
                JOptionPane.showMessageDialog(null, "File selezionato non è un'immagine valida.");
                return null;
            }

            String targetPath = "resources/copertineLibri/" + UUID.randomUUID().toString() + "." + getEstensioneFile(sourceFile);

            try {
                // creo la cartella di destinazione se non esiste
                Path targetDirectory = Paths.get("resources/copertineLibri");
                if (!Files.exists(targetDirectory)) {
                    Files.createDirectories(targetDirectory);
                }

                // copio il file selezionato nella cartella risorse
                Path targetFile = Paths.get(targetPath);
                Files.copy(sourceFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);

                return targetPath;
            } catch (IOException e) {
                System.err.println("Errore nel copiare o rinominare l'immagine: " + e.getMessage());
                return null;
            }
        }

    // Verifica se il file è un'immagine
    private static boolean estensioneValida(File file) {
        String extension = getEstensioneFile(file);
        return extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png");
    }

    // Estrai l'estensione del file
    private static String getEstensioneFile(File file) {
        String nomeFile = file.getName();
        int indicePunto = nomeFile.lastIndexOf(".");
        // il file non ha un estensione, non sono riuscito a trovare il punto.
        if (indicePunto == -1) {
            return "";
        }
        return nomeFile.substring(indicePunto + 1);
    }

    public static void eliminaCopertina(String percorsoCopertina){
       if(!percorsoCopertina.equals(" ")) {
           Path path = Paths.get(percorsoCopertina);

           try {
               Files.delete(path); // Elimina il file
               System.out.println("File eliminato con successo.");
           } catch (NoSuchFileException e) {
               System.err.println("Il file non esiste: " + percorsoCopertina);
           } catch (IOException e) {
               System.err.println("Errore durante l'eliminazione del file: " + e.getMessage());
           }
       }


    }

}


