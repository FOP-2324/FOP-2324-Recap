package r09.sequence.operation;

import r09.sequence.Sequence;

import java.util.Iterator;
import java.util.function.Function;

import static org.tudalgo.algoutils.student.Student.crash;

public class TransformingSequence<T, R> implements Sequence<R> {
    private final Sequence<? extends T> sequence;
    private final Function<? super T, ? extends R> function;

    public TransformingSequence(Sequence<? extends T> sequence, Function<? super T, ? extends R> function) {
        this.sequence = sequence;
        this.function = function;
    }
//    @Override
    public Iterator<R> iterator() {
        return new Iterator<>() {
            private final Iterator<? extends T> iterator = sequence.iterator();
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return function.apply(iterator.next());
            }
        };
    }
}