package r09.sequence.collect;

import r09.sequence.Sequence;

import java.util.Iterator;
import java.util.function.BinaryOperator;

public class BinaryOpFoldCollector<T> implements SequenceCollector<T, T> {
    private final T initial;
    private final BinaryOperator<T> operator;

    public BinaryOpFoldCollector(T initial, BinaryOperator<T> operator) {
        this.initial = initial;
        this.operator = operator;
    }

    @Override
    public T collect(Sequence<? extends T> sequence) {
        T result = initial;
        final Iterator<? extends T> iterator = sequence.iterator();
        while(iterator.hasNext()) {
            result = operator.apply(result, iterator.next());
        }
        return result;
    }
}