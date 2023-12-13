package r08.preconditions;

import static org.tudalgo.algoutils.student.Student.crash;

public class Preconditions {
    private Preconditions() {
    }

    /**
     * Throws an {@link ArrayIsNullException} iff the given primary array is null.
     *
     * @param primaryArray The primary array to be validated
     * @throws ArrayIsNullException Thrown when the given primary array is null
     */
    public static void checkPrimaryArrayNotNull(double[][] primaryArray) {
        crash(); // TODO: H3.1 - remove if implemented
    }

    /**
     * Throws an {@link AtIndexException} iff any secondary array in primaryArray is
     * null.
     * The exception is thrown with the smallest index possible, i.e. the first
     * occurrence of null in the primary array triggers the exception.
     *
     * @param primaryArray The primary array containing the secondary arrays to be
     *                     validated
     * @throws AtIndexException Thrown when any secondary array in primaryArray is
     *                          null
     */
    public static void checkSecondaryArraysNotNull(double[][] primaryArray) {
        crash(); // TODO: H3.1 - remove if implemented
    }

    /**
     * Throws a {@link WrongNumberException} iff the {@code number} is negative.
     *
     * @param number The number to be validated
     * @throws WrongNumberException Thrown when the {@code number} is negative
     */
    public static void checkNumberNotNegative(double number) {
        crash(); // TODO: H3.1 - remove if implemented
    }

    /**
     * Throws an {@link AtIndexPairException} iff any value in any secondary array
     * in {@code primaryArray} is negative or bigger than {@code max}. The
     * {@link AtIndexPairException}
     * will be thrown for the smallest indices possible, i.e. for the first
     * occurrence of an invalid value.
     *
     * @param primaryArray The primary array containing the secondary arrays with
     *                     values to be validated
     * @param max          The maximum that may not be exceeded by any value in the
     *                     secondary arrays
     * @throws AtIndexPairException Thrown when any value in any secondary array
     *                              {@code primaryArray} is negative or
     *                              bigger than {@code max}
     */
    public static void checkValuesInRange(double[][] primaryArray, double max) {
        crash(); // TODO: H3.1 - remove if implemented
    }
}