package model;

public enum GenereLibro implements Formattabile {
    AUDIOLIBRI("Audiolibri"),
    ARTE("Arte"),
    DESIGN("Design"),
    FANTASY("Fantasy"),
    HORROR("Horro"),
    STORICO("Storico"),
    POESIE("Poesie"),
    THRILLER("Thriller"),
    FILOSOFICO("Filosofico"),
    GIALLO("Giallo"),
    AUTOBIOGRAFICO("Autobiografico"),
    SAGGIO("Saggio"),
    SOCIALE("Sociale"),
    DISTOPICO("Distopico"),
    POLITICO("Politico"),
    DRAMMATICO("Drammatico"),
    NARRATIVA("Narrativa"),
    EDUCAZIONE("Educazione"),
    AVVENTURA("Avventura"),
    ATTUALITA("Attualità"),
    NOIR("Noir"),
    CRIME("Crime"),
    ROMANZO("Romanzo"),
    AUTOBIOGRAFIA("Autobiografia"),
    OTHER("Audiolibri");

    private final String label;

    GenereLibro(String label){
        this.label=label;
    }

    @Override
    public String formatoDisplay() {
        return label;
    }
}
