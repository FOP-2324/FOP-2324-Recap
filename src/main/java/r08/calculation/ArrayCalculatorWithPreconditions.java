package r08.calculation;

import r08.preconditions.ArrayIsNullException;
import r08.preconditions.AtIndexException;
import r08.preconditions.AtIndexPairException;
import r08.preconditions.WrongNumberException;

import static r08.preconditions.Preconditions.*;

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
    public double addUp(double[][] theArray, double max) throws ArrayIsNullException, AtIndexException, WrongNumberException, AtIndexPairException {
        checkPrimaryArrayNotNull(theArray);
        checkSecondaryArraysNotNull(theArray);
        checkNumberNotNegative(max);
        checkValuesInRange(theArray, max);

        double result = 0;
        for(int i = 0; i < theArray.length; i++) {
            for(int j = 0; j < theArray[i].length; j++) {
                result += theArray[i][j];
            }
        }
        return result;
    }
}