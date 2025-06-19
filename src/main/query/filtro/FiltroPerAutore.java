package main.query.filtro;

import main.model.Libro;

public class FiltroPerAutore implements FiltroArchivio{

    private String nome,cognome;

    FiltroPerAutore(String nome,String cognome){
        this.nome=nome;
        this.cognome=cognome;
    }

    @Override
    public boolean filtra(Libro l) {
        return l.getAutori().stream().
                anyMatch(autore -> autore.getNome().equals(this.nome) &&
                        autore.getCognome().equals(this.cognome));

    }
}
