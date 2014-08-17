package com.fyodor.generators;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.newHashSet;

final class Sampler<T> {

    private final Generator<? extends T> generatorOfT;

    private Sampler(final Generator<? extends T> generatorOfT) {
        this.generatorOfT = generatorOfT;
    }

    public Sample<T> sample(final int sampleSize) {
        return new Sample(Stream.generate(generatorOfT::next)
                .limit(sampleSize)
                .collect(Collectors.toList()));
    }

    public static <T> Sampler<T> from(final Generator<? extends T> generatorOfT) {
        return new Sampler<>(generatorOfT);
    }

    static final class Sample<T> implements Iterable<T> {

        private final List<T> listOfT;

        Sample(final List<T> listOfT) {
            this.listOfT = listOfT;
        }

        Set<T> unique() {
            return newHashSet(listOfT);
        }

        @Override
        public Iterator<T> iterator() {
            return listOfT.iterator();
        }
    }
}
