package main.model;

public enum GenereLibro implements Formattabile {
    AUDIOLIBRI("Audiolibri"),
    ARTE("Arte"),
    DESIGN("Design"),
    FANTASY("Fantasy"),
    HORROR("Horro"),
    SOTRICO("Storico"),
    POESIE("Poesie"),
    THRILLER("Thriller"),
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
