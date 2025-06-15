package main.model;

public enum StatoLibro implements Formattabile {
    LETTO("Letto"),
    DA_LEGGERE("Da leggere"),
    IN_LETTURA("In lettura"),
    NULL("No Info");

    private final String label;

    StatoLibro(String label){
        this.label=label;
    }


    @Override
    public String formatoDisplay() {
        return label;
    }
}
