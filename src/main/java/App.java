import archivio.ArchivioLibri;
import archivio.ArchivioLibriJSON;
import controller.ControllerLibro;
import gui.MainFrame;

import javax.swing.*;


public class App {
    public static void main(String[] args) {

        ArchivioLibri archivio =new ArchivioLibriJSON("resources/Archiviolibri.json");
        ControllerLibro controller = new ControllerLibro(archivio);

        SwingUtilities.invokeLater(() -> new MainFrame(controller));

    }
}
