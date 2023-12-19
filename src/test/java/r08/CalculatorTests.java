package r08;

import org.junit.jupiter.api.Assertions;
import r08.calculation.ArrayCalculator;
import r08.calculation.ArrayCalculatorWithPreconditions;
import r08.calculation.ArrayCalculatorWithRuntimeExceptions;
import r08.preconditions.ArrayIsNullException;
import r08.preconditions.AtIndexException;
import r08.preconditions.AtIndexPairException;
import r08.preconditions.WrongNumberException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.tudalgo.algoutils.student.Student.crash;

class CalculatorTests {
    private static ArrayCalculatorWithRuntimeExceptions runtimeExceptionCalculator;
    private static ArrayCalculatorWithPreconditions preconditionCalculator;

    @BeforeAll
    static void beforeAll() {
        runtimeExceptionCalculator = new ArrayCalculatorWithRuntimeExceptions();
        preconditionCalculator = new ArrayCalculatorWithPreconditions();
    }

    // Tests for ArrayCalculatorWithRuntimeExceptions

    @Test
    void runtimeCalculator_arrayGiven_calculatesSum() {
        testSum(runtimeExceptionCalculator, new double[][] { new double[] { 2, 42 } }, Integer.MAX_VALUE, 44);
    }

    @Test
    void runtimeCalculator_primaryArrayNull_throwsException() {
        testException(runtimeExceptionCalculator, null, Integer.MAX_VALUE, NullPointerException.class,
                "Primary array is void!");
    }

    @Test
    void runtimeCalculator_secondaryArrayNull_throwsException() {
        testException(runtimeExceptionCalculator, new double[][] { null }, Integer.MAX_VALUE,
                NullPointerException.class,
                "Secondary array at 0 is void!");
    }

    @Test
    void runtimeCalculator_maxNegative_throwsException() {
        testException(runtimeExceptionCalculator, new double[][] { new double[0] }, -1, ArithmeticException.class,
                "Upper bound is negative!");
    }

    @Test
    void runtimeCalculator_valueNegative_throwsException() {
        testException(runtimeExceptionCalculator, new double[][] { new double[] { -1 } }, Integer.MAX_VALUE,
                ArithmeticException.class,
                "Value at (0,0) is not in range!");
    }

    // Tests for ArrayCalculatorWithPreconditions

    @Test
    void preconditionCalculator_arrayGiven_calculatesSum() {
        testSum(preconditionCalculator, new double[][] { new double[] { 12, 42 } }, 100, 54);
    }

    @Test
    void preconditionCalculator_primaryArrayNull_throwsException() {
        testException(preconditionCalculator, null, Integer.MAX_VALUE, ArrayIsNullException.class,
                "Array is null!");
    }

    @Test
    void preconditionCalculator_secondaryArrayNull_throwsException() {
        testException(preconditionCalculator, new double[][] { null }, Integer.MAX_VALUE,
                AtIndexException.class,
                "Index: 0");
    }

    @Test
    void preconditionCalculator_maxNegative_throwsException() {
        testException(preconditionCalculator, new double[][] { new double[0] }, -1, WrongNumberException.class,
                "-1.0");
    }

    @Test
    void preconditionCalculator_valueNegative_throwsException() {
        testException(preconditionCalculator, new double[][] { new double[] { -1 } }, Integer.MAX_VALUE,
                AtIndexPairException.class,
                "Index: (0,0)");
    }

    /**
     * Takes an object that implements {@link ArrayCalculator} and verifies that a
     * call of {@link ArrayCalculator#addUp(double[][], double)} with the given
     * parameters returns the expected sum. Will fail the unit test if the wrong
     * sum is calculated or any exception is thrown.
     *
     * @param sut         The object implementing {@link ArrayCalculator} that
     *                    should be verified
     * @param array       The primary array to be used when calling
     *                    {@link ArrayCalculator#addUp(double[][], double)}
     * @param max         The max value to be used when calling
     *                    {@link ArrayCalculator#addUp(double[][], double)}
     * @param expectedSum The expected sum to be calculated
     */
    private void testSum(ArrayCalculator sut, double[][] array, double max, double expectedSum) {
        try {
            Assertions.assertEquals(expectedSum, sut.addUp(array, max));
        }
        catch (Exception e) {
            Assertions.assertDoesNotThrow(() -> sut.addUp(array, max));
        }
    }

    /**
     * Takes an object that implements {@link ArrayCalculator} and verifies that a
     * call of {@link ArrayCalculator#addUp(double[][], double)} with the given
     * parameters
     * results in an exception being thrown. Will fail the unit test if the expected
     * exception
     * is not thrown or the message of the exception is wrong.
     *
     * @param sut               The object implementing {@link ArrayCalculator} that
     *                          should be verified
     * @param array             The primary array to be used when calling
     *                          {@link ArrayCalculator#addUp(double[][], double)}
     * @param max               The max value to be used when calling
     *                          {@link ArrayCalculator#addUp(double[][], double)}
     * @param expectedException The exact type of the expected exception being
     *                          thrown
     * @param expectedExceptionMessage   The exact message of the expected exception being
     *                          thrown
     */
    private <T extends Throwable> void testException(ArrayCalculator sut, double[][] array, double max, Class<T> expectedException,
                                                     String expectedExceptionMessage) {
        var exc = Assertions.assertThrowsExactly(expectedException, () -> sut.addUp(array, max));
        if(!expectedExceptionMessage.equals(exc.getMessage())) {
            throw new AssertionError("%s : %s".formatted(expectedExceptionMessage, exc.getMessage()));
        }
    }
}