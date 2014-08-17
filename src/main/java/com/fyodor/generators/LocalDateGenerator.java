package com.fyodor.generators;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import java.time.LocalDate;

final class LocalDateGenerator implements Generator<LocalDate> {

    private static final LocalDate DEFAULT_UPPER_BOUND = LocalDate.of(2999, 12, 31);
    private static final LocalDate DEFAULT_LOWER_BOUND = LocalDate.of(0, 1, 1);

    private final RandomValues randomValues;
    private final Range<LocalDate> range;

    public LocalDateGenerator(final RandomValues randomValues, final Range<LocalDate> range) {
        this.randomValues = randomValues;
        this.range = range;
    }

    @Override
    public LocalDate next() {
        final long lowerBound = lowerBound().toEpochDay();
        final long upperBound = upperBound().toEpochDay();
        return LocalDate.ofEpochDay(randomValues.randomLong(lowerBound, upperBound));
    }

    private LocalDate lowerBound() {
        if (!range.hasLowerBound()) {
            return DEFAULT_LOWER_BOUND;
        }

        final LocalDate lowerEndpoint = range.lowerEndpoint();

        return range.lowerBoundType() == BoundType.CLOSED
                ? lowerEndpoint
                : lowerEndpoint.plusDays(1);
    }

    private LocalDate upperBound() {
        if (!range.hasUpperBound()) {
            return DEFAULT_UPPER_BOUND;
        }

        final LocalDate upperEndpoint = range.upperEndpoint();

        return range.upperBoundType() == BoundType.CLOSED
                ? upperEndpoint
                : upperEndpoint.minusDays(1);
    }
}
