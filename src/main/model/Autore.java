package main.model;

public final class Autore {
    private String nome;
    private String cognome;
    private String citta;
    private String dataNascita;

    Autore(String nome,String cognome,String citta,String dataNascita){
        this.nome=nome;
        this.cognome=cognome;
        this.citta=citta;
        this.dataNascita=dataNascita;
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

}
