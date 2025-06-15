package main.model;

public enum ValutazioneLibro implements Formattabile {
    INSUFFICIENTE("★☆☆☆☆"),
    SUFFICIENTE("★★☆☆☆"),
    BUONO("★★★☆☆"),
    OTTIMO("★★★★☆"),
    ECCELLENTE("★★★★★"),
    NULL("☆☆☆☆☆");
    private final String label;

    ValutazioneLibro(String label){
        this.label=label;
    }

    @Override
    public String formatoDisplay() {
        return label;
    }
}
