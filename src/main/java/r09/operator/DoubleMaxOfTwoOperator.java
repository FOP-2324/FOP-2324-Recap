package r09.operator;

import java.util.function.BinaryOperator;

public class DoubleMaxOfTwoOperator implements BinaryOperator<Double> {
    @Override
    public Double apply(Double left, Double right) {
        return left.compareTo(right) > 0 ? left : right;
    }
}