package uk.org.fyodor.generators;

import java.util.stream.Stream;

public interface Generator<T> {

    public T next();

    default Stream<T> stream() {
        return Stream.generate(Generator.this::next);
    }
}
