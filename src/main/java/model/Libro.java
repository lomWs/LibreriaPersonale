package model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Libro {
    private List<Autore> autori ;
    private String titolo;
    private String ISBN;

    private StatoLibro stato; //stato di lettura, non letto, letto ed iniziato
    private Set<GenereLibro> generi;
    private ValutazioneLibro valutazione;



    Libro(Builder builder){
        this.autori = builder.autori == null ?
                    new ArrayList<>() : new ArrayList<>(builder.autori);//autore è immutabile
        this.titolo = builder.titolo;
        this.ISBN = builder.ISBN;
        this.stato=builder.stato;
        this.generi=builder.generi == null ?
                    new HashSet<>() : new HashSet<>(builder.generi);//generi sono enum, dunque immutabili
        this.valutazione= builder.valutazione;

    }

    public static class Builder{
        //parametri obligatori
        private  List<Autore> autori ;
        private String titolo;
        private String ISBN;

        //parametri facoltativi
        private StatoLibro stato = StatoLibro.NULL; //stato di lettura
        private Set<GenereLibro> generi ;
        private ValutazioneLibro valutazione= ValutazioneLibro.NULL;



        public Builder(List<Autore> autori,String titolo,String ISBN){
            this.autori=autori;
            this.titolo=titolo;
            this.ISBN=ISBN;
        }

        public Builder stato(StatoLibro stato){
            this.stato=stato;
            return this;
        }

        public Builder generi(Set<GenereLibro> generi){
            this.generi=generi;
            return this;
        }

        public Builder valutazione(ValutazioneLibro valutazione){
            this.valutazione=valutazione;
            return this;
        }

        public Libro build(){
            return new Libro(this);
        }



    }

    public String getTitolo() {
        return titolo;
    }

    public List<Autore> getAutori() {
        return autori;
    }

    public Set<GenereLibro> getGeneri() {
        return generi;
    }

    public ValutazioneLibro getValutazione() {
        return valutazione;
    }

    public StatoLibro getStato() {
        return stato;
    }

    public String getISBN() {
        return ISBN;
    }

}
