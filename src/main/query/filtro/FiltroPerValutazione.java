package main.query.filtro;

import main.model.Libro;
import main.model.ValutazioneLibro;

public class FiltroPerValutazione implements FiltroArchivio{

    private ValutazioneLibro valutazione;

    FiltroPerValutazione(ValutazioneLibro v){
        this.valutazione = v;
    }

    @Override
    public boolean filtra(Libro l) {
        return l.getValutazione().equals(valutazione);
    }

    public boolean filtraMaggiore(Libro l) {
        return l.getValutazione().ordinal() > valutazione.ordinal();
    }

    public boolean filtraMaggioreUguale(Libro l) {
        return l.getValutazione().ordinal() >= valutazione.ordinal();
    }

    public boolean filtraMinore(Libro l) {
        return l.getValutazione().ordinal() < valutazione.ordinal();
    }

    public boolean filtraMinoreUguale(Libro l) {
        return l.getValutazione().ordinal() <= valutazione.ordinal();
    }


}
