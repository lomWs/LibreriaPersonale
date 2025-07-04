package gui.temi;


import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class TemaChiaroFactory implements TemaFactory {
    private final Color colorePrincipaleSfondo = new Color(245, 245, 245);      // sfondo finestre
    private final Color coloreSecodarioSfondo = new Color(255, 255, 255);// sfondo pannelli
    private final Color coloreTerziarioSfondo = new Color(200,200,200);
    private final Color colorePrimarioBottone = new Color(30, 144, 255);        // blu principale
    private final Color coloreHover = new Color(70, 160, 255);                  // hover bottone
    private final Color coloreTesto = Color.BLACK;

    private final Font fontBase = new Font("SansSerif", Font.PLAIN, 14);
    private final Font fontTitolo = new Font("SansSerif", Font.BOLD, 20);

    @Override
    public JButton creaBottonePrincipale(String text) {
        JButton button = new JButton(text);
        button.setFont(fontBase);
        button.setBackground(colorePrimarioBottone);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(coloreHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(colorePrimarioBottone);
            }
        });
        return button;
    }

    @Override
    public JButton creaBottoneElimina(String text) {
        JButton button = new JButton(text);
        button.setFont(fontBase);
        button.setBackground(new Color(220, 80, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 100, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 80, 80));
            }
        });
        return button;
    }

    @Override
    public JTextField creaTextField() {
        JTextField field = new JTextField();
        field.setFont(fontBase);
        field.setBackground(Color.WHITE);
        field.setForeground(coloreTesto);
        field.setCaretColor(coloreTesto);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    @Override
    public JComboBox<?> creaComboBox() {
        JComboBox<?> comboBox = new JComboBox<>();

        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = new BasicComboPopup(comboBox) {
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroll = super.createScroller();
                        scroll.setVerticalScrollBar(GestoreTema.getInstance().getFactoryTemaAttuale().creaScrollBar());
                        scroll.getViewport().setBackground(GestoreTema.getInstance().getFactoryTemaAttuale().getColoreSecondarioSfondo());
                        return scroll;
                    }
                };
                return popup;
            }
        });

        comboBox.setFont(fontBase);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(coloreTesto);
        return comboBox;
    }

    @Override
    public JScrollBar creaScrollBar() {
        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setOpaque(false);
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle bounds) {}

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle bounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(180, 180, 180, 180));
                g2.fillRoundRect(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8, 12, 12);
                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                return b;
            }
        });

        return scrollBar;
    }

    @Override
    public JScrollPane creaScrollPane(Component view) {
        JScrollPane pane = new JScrollPane(view);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setBorder(null);
        pane.getViewport().setBorder(null);
        pane.setVerticalScrollBar(this.creaScrollBar());
        return pane;
    }

    @Override
    public Color getColorePrimarioSfondo() {
        return colorePrincipaleSfondo;
    }

    @Override
    public Color getColoreSecondarioSfondo() {
        return coloreSecodarioSfondo;
    }

    @Override
    public Color getColorePrimarioBottone() {
        return colorePrimarioBottone;
    }

    @Override
    public Color getColoreTesto() {
        return coloreTesto;
    }

    @Override
    public Color getColoreHover() {
        return coloreHover;
    }

    @Override
    public Font getFontPrimario() {
        return fontBase;
    }

    @Override
    public Font getFontTitolo() {
        return fontTitolo;
    }

    @Override
    public Color getColoreTerziarioSfondo() {
        return coloreTerziarioSfondo;
    }
}
