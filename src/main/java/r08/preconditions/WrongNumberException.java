package r08.preconditions;

/**
 * Thrown when a max value is negative.
 */
public class WrongNumberException extends Exception {
    public WrongNumberException(double value) {
        super(String.valueOf(value));
    }
}