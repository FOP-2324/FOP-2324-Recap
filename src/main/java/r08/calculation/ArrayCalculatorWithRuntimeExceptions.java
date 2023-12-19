package r08.calculation;

import static org.tudalgo.algoutils.student.Student.crash;

public class ArrayCalculatorWithRuntimeExceptions implements ArrayCalculator {
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
    public double addUp(double[][] theArray, double max) throws NullPointerException, ArithmeticException {
        if(theArray == null) {
            throw new NullPointerException("Primary array is void!");
        }
        //assert theArray != null;

        for(int i = 0; i < theArray.length; i++) {
            if(theArray[i] == null) {
                throw new NullPointerException("Secondary array at " + i + " is void!");
            }
        }

        if(max < 0) {
            throw new ArithmeticException("Upper bound is negative!");
        }

        for(int i = 0; i < theArray.length; i++) {
            for(int j = 0; j < theArray[i].length; j++) {
                if(theArray[i][j] < 0 || theArray[i][j] > max) {
                    throw new ArithmeticException("Value at (" + i + "," + j + ") is not in range!");
                }
            }
        }
        double result = 0;
        for(int i = 0; i < theArray.length; i++) {
            for(int j = 0; j < theArray[i].length; j++) {
                result += theArray[i][j];
            }
        }
        return result;
    }
}