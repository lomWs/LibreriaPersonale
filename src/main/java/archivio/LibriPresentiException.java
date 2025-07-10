package archivio;

public class LibriPresentiException extends RuntimeException {
    /**
    * Eccezione sollevata nel il caso in cui un libro sia già presente all'interno dell'archivio
    * */

    public LibriPresentiException() {
        super("Il libro è già stato inserito nell'archivio");
    }

    public LibriPresentiException(String message) {
        super(message);
    }
}
