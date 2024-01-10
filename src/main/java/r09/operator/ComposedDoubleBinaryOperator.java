package r09.operator;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;

public class ComposedDoubleBinaryOperator implements BinaryOperator<Double> {
    /**
     * First operator.
     */
    private final BinaryOperator<Double> op1;

    /**
     * Second operator.
     */
    private final BinaryOperator<Double> op2;

    /**
     * Third operator.
     */
    private final BinaryOperator<Double> op3;

    /**
     * Constructor initializes the three operators.
     *
     * @param op1 First operator.
     * @param op2 Second operator.
     * @param op3 Third operator.
     */
    public ComposedDoubleBinaryOperator(BinaryOperator<Double> op1, BinaryOperator<Double> op2, BinaryOperator<Double> op3) {
        // Assign first parameter to first operator
        this.op1 = op1;

        // Assign second parameter to second operator
        this.op2 = op2;

        // Assign third parameter to third operator
        this.op3 = op3;
    }

    /**
     * First applies the first and the second operator to the given parameters.
     * Then applies the third operator to the result of the application of the
     * first two operators.
     *
     * @param left  The first parameter.
     * @param right The second parameter.
     * @return Application of the third operator on the results of the
     * first and second operator.
     */
    @Override
    public Double apply(Double left, Double right) {
        // Apply first operator to given parameters
        Double result1 = op1.apply(left, right);

        // Apply second operator to given parameters
        Double result2 = op2.apply(left, right);

        // Return application of third operator to the intermediate results
        return op3.apply(result1, result2);
    }
}