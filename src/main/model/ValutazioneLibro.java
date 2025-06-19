package main.model;

public enum ValutazioneLibro implements Formattabile {
    //non cambia mai ordine, nel codice uso ordinal
    NULL("☆☆☆☆☆"),
    INSUFFICIENTE("★☆☆☆☆"),
    SUFFICIENTE("★★☆☆☆"),
    BUONO("★★★☆☆"),
    OTTIMO("★★★★☆"),
    ECCELLENTE("★★★★★");

    private final String label;

    ValutazioneLibro(String label){
        this.label=label;
    }

    @Override
    public String formatoDisplay() {
        return label;
    }
}
