package r09.sequence;

import r09.basic.BasicFactory;

import java.util.Iterator;

public class BasicFactorySequence<T> implements Sequence<T> {
    private final BasicFactory<T> factory;

    public BasicFactorySequence(BasicFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return factory.create();
            }
        };
    }
}