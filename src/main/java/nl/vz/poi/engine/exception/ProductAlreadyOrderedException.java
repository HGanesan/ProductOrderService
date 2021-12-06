package nl.vz.poi.engine.exception;

public class ProductAlreadyOrderedException extends Exception {
    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    public ProductAlreadyOrderedException(String message) {
        super(message);
    }
}
