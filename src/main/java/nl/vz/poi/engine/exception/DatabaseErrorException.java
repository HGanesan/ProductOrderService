package nl.vz.poi.engine.exception;

public class DatabaseErrorException extends Exception {
    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    public DatabaseErrorException(String message) {
        super(message);
    }
}
