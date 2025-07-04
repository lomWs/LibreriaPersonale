package gui.temi;

public final class GestoreTema {
    private static TemaFactory temaFactoryDefault;
    private static GestoreTema istanza = null;

    private GestoreTema(){
        //tema di default;
        temaFactoryDefault = new TemaScuroFactory();
    }

    public static  GestoreTema getInstance() {
        if (istanza == null) {
            istanza= new GestoreTema();
        }
        return istanza;
    }

    public  TemaFactory getFactoryTemaAttuale() {
        return temaFactoryDefault;
    }

    public static void setFactoryTema(TemaFactory temaFactory) {
        temaFactoryDefault = temaFactory;
    }
}
