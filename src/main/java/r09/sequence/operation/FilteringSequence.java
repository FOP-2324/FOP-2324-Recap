package r09.sequence.operation;

import r09.sequence.Sequence;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

public class FilteringSequence<T> implements Sequence<T> {
    private final Sequence<? extends T> sequence;
    private final Predicate<? super T> predicate;

    public static <T> Function<Sequence<T>, Sequence<T>> of(Predicate<? super T> predicate) {
        return sequence -> new FilteringSequence<>(sequence, predicate);
    }

    public FilteringSequence(Sequence<? extends T> sequence, Predicate<? super T> predicate) {
        this.sequence = sequence;
        this.predicate = predicate;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final Iterator<? extends T> iterator = sequence.iterator();
            private T next;

            @Override
            public boolean hasNext() {
                if(next != null) {
                    return true;
                }
                while(iterator.hasNext()) {
                    next = iterator.next();
                    if(predicate.test(next)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                final T result = next;
                next = null;
                return result;
            }
        };
    }
}