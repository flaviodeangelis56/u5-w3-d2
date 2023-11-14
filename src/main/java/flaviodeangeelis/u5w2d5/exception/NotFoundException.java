package flaviodeangeelis.u5w2d5.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(int id) {
        super("Elemento con id " + id + " non trovato!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
