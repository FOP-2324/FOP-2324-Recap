package r09.sequence.collect;

import r09.sequence.Sequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToListCollector<T> implements SequenceCollector<T, List<T>> {
    @Override
    public List<T> collect(Sequence<? extends T> sequence) {
        final List<T> result = new ArrayList<>();
        final Iterator<? extends T> iterator = sequence.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}