package model;

public final class Autore {
    private String nome;
    private String cognome;
    private String citta;
    private String dataNascita;

    public Autore(String nome,String cognome,String citta,String dataNascita){
        this.nome=nome;
        this.cognome=cognome;
        this.citta=citta;
        this.dataNascita=dataNascita;
    }

    public Autore(String nome,String cognome){
        this.nome=nome;
        this.cognome=cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCitta() {
        return citta;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    @Override
    public String toString() {
        return this.nome +" "+ this.cognome;
    }
}
