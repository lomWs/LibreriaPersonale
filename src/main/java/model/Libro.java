package model;
import java.io.Serializable;
import java.util.*;

public final class Libro implements Serializable {

    /**
     * Classe Libro permette tramite Builder di avere molta modularità nella costruzione dell'oggetto infatti i soli campi
     * resi obligatori sono autori,titolo,ISBN.
     * */




    private List<Autore> autori ;
    private String titolo;
    private String ISBN;

    private StatoLibro stato; //stato di lettura, non letto, letto ed iniziato
    private Set<GenereLibro> generi;
    private ValutazioneLibro valutazione;
    private String percorsoCopertina = " ";



    Libro(LibroBuilder builder){
        this.autori = builder.autori == null ?
                    new ArrayList<>() : new ArrayList<>(builder.autori);//autore è immutabile
        this.titolo = builder.titolo;
        this.ISBN = builder.ISBN;
        this.stato=builder.stato;
        this.generi=builder.generi == null ?
                    new HashSet<>() : new HashSet<>(builder.generi);//generi sono enum, dunque immutabili
        this.valutazione= builder.valutazione;

    }

    public static class LibroBuilder{
        //parametri obligatori
        private  List<Autore> autori ;
        private String titolo;
        private String ISBN;

        //parametri facoltativi
        private StatoLibro stato = StatoLibro.NULL; //stato di lettura
        private Set<GenereLibro> generi ;
        private ValutazioneLibro valutazione= ValutazioneLibro.NULL;



        public LibroBuilder(List<Autore> autori,String titolo,String ISBN){
            this.autori=autori;
            this.titolo=titolo;
            this.ISBN=ISBN;
        }

        public LibroBuilder stato(StatoLibro stato){
            this.stato=stato;
            return this;
        }

        public LibroBuilder generi(Set<GenereLibro> generi){
            this.generi=generi;
            return this;
        }

        public LibroBuilder valutazione(ValutazioneLibro valutazione){
            this.valutazione=valutazione;
            return this;
        }

        public Libro build(){
            return new Libro(this);
        }


    }

    public String getTitolo() {
        return this.titolo;
    }

    public List<Autore> getAutori() {
        return this.autori;
    }

    public Set<GenereLibro> getGeneri() {
        return this.generi;
    }

    public ValutazioneLibro getValutazione() {
        return this.valutazione;
    }

    public StatoLibro getStato() {
        return this.stato;
    }

    public String getISBN() {
        return this.ISBN;
    }

    public String getPercorsoCopertina() {
        return this.percorsoCopertina;
    }

    public void setStato(StatoLibro s){
        this.stato=s;
    }
    public void setValutazione(ValutazioneLibro v){
        this.valutazione=v;
    }

    public void setPercorsoCopertina(String percorsoCopertina) {
        this.percorsoCopertina = percorsoCopertina;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(this.ISBN, libro.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.ISBN);
    }

    @Override
    public String toString() {
        return this.getTitolo() + " "
               +this.getISBN() + " "
                +this.getAutori() + " "
                + this.getGeneri();
    }

}
