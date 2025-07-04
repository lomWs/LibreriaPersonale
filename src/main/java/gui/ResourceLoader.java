package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ResourceLoader {
    public static ImageIcon loadIcon(String path, int w, int h) {
        URL url = ResourceLoader.class.getResource(path);
        if (url == null) return null;
        Image img = new ImageIcon(url).getImage();
        return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }
}
