package r09.sequence;

import java.util.Iterator;

import static org.tudalgo.algoutils.student.Student.crash;

public class FibonacciSequence implements Sequence<Integer> {
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            private int current = 0;
            private int next = 1;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                int nextTemp = current + next;
                current = next;
                next = nextTemp;
                return current;
            }
        };
    }
}