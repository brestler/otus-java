package atm.exceptions;

public class CellOverFilledException extends RuntimeException {

    public CellOverFilledException(String message) {
        super(message);
    }
}
