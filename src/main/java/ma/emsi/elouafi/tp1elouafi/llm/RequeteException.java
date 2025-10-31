package ma.emsi.elouafi.tp1elouafi.llm;

public class RequeteException extends Exception {

    public RequeteException(String message) {
        super(message);
    }

    public RequeteException(String message, Throwable cause) {
        super(message, cause);
    }

    // Optionnel — utile pour concaténer deux textes
    public RequeteException(String message, String details) {
        super(message + " : " + details);
    }
}
