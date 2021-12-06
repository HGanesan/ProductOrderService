package nl.vz.poi.engine.exception;

public class InvalidResponseCodeException extends Exception {
    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public InvalidResponseCodeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
