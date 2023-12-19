package r08.preconditions;

/**
 * Thrown when a secondary array is null.
 */
public class AtIndexException extends RuntimeException {
    public AtIndexException(int value) {
        super("Index: %s".formatted(value));
    }
}