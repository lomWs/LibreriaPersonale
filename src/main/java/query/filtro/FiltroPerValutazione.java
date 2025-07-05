package query.filtro;


import model.Libro;
import model.ValutazioneLibro;

public class FiltroPerValutazione extends AbstractFiltroArchivio<ValutazioneLibro> {

    //private ValutazioneLibro valutazione;

    public FiltroPerValutazione(ValutazioneLibro v){
        super(v);
    }

    @Override
    public boolean filtra(Libro l) {
        return l.getValutazione().equals(this.parametroFiltro);
    }

    public boolean filtraMaggiore(Libro l) {
        return l.getValutazione().ordinal() > this.parametroFiltro.ordinal();
    }

    public boolean filtraMaggioreUguale(Libro l) {
        return l.getValutazione().ordinal() >= this.parametroFiltro.ordinal();
    }

    public boolean filtraMinore(Libro l) {
        return l.getValutazione().ordinal() < this.parametroFiltro.ordinal();
    }

    public boolean filtraMinoreUguale(Libro l) {
        return l.getValutazione().ordinal() <= this.parametroFiltro.ordinal();
    }


}
