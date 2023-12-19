package r08.preconditions;

/**
 * Thrown when any value in a secondary array is outside the valid range.
 */
public class AtIndexPairException extends Exception {
    public AtIndexPairException(int value1, int value2) {
        super("Index: (%s,%s)".formatted(value1, value2));
    }
}