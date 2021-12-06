package nl.vz.poi.engine.exception;

public class InvalidEmailIdException extends Exception {
    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    public InvalidEmailIdException(String message) {
        super(message);
    }
}
