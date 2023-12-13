package r08.calculation;

import static org.tudalgo.algoutils.student.Student.crash;

public class ArrayCalculatorWithPreconditions implements ArrayCalculator {
    /**
     * Adds up all double values in an array of arrays of double values. The double
     * values may not be negative or bigger than max.
     *
     * @param theArray The primary array containing the secondary arrays with their
     *                 double values
     * @param max      The maximum value any double value contained in the arrays may
     *                 have
     * @return double The sum of all double values contained on all secondary
     *                arrays.
     */
    @Override
    public double addUp(double[][] theArray, double max) {
        return crash(); // TODO: H3.2 - remove if implemented
    }
}