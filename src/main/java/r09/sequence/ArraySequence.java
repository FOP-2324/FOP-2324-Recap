package r09.sequence;

import java.util.Iterator;

import static org.tudalgo.algoutils.student.Student.crash;

public class ArraySequence<T> implements Sequence<T> {
    private final T[] values;

    public ArraySequence(T[] values) {
        this.values = values;
    }
//    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @Override
            public T next() {
                return values[index++];
            }
        };
    }
}