package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;


public  class GestoreCopertina {

    /**
     * GestoreCopertina si occupa della gestione delle immagini assegnate come copertina dei libri e mostrate
     * nel GridBoxLibroPanel
     *
     * @See GridBoxLibroPanel
     * */


    //prende la copertina dalla locazione fisica e la trasforma in un oggetto da mostrare nella GUI
    public static ImageIcon loadIcon(String imagePath, int width, int height) {
        if(imagePath.equals(" "))
            return null;
        try {

            Image image = ImageIO.read(new File(imagePath));


            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);


            return new ImageIcon(scaledImage);

        } catch (Exception e) {

            System.err.println("Errore nel caricare l'immagine: " + e.getMessage());
            return null;
        }
    }

    // Metodo che copia e rinomina l'immagine usando l'ISBN del libro
    public static String salvataggioCopertina(File sourceFile) {

        if (!estensioneValida(sourceFile)) {
            JOptionPane.showMessageDialog(null, "File selezionato non è un'immagine valida.");
            return null;
        }

        //uso UUID per avere un nome univoco e non avere collisioni
        String targetPath = "resources/copertineLibri/" + UUID.randomUUID().toString() + "." + getEstensioneFile(sourceFile);

        try {

            Path targetDirectory = Paths.get("resources/copertineLibri");
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }


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


