package hu.lev.bead.neptun.exception;

public class InDatabaseException extends RuntimeException{
    public InDatabaseException(String message) {
        super(message);
    }
}
