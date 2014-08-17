package com.fyodor.generators;

import com.google.common.collect.Range;
import org.junit.Test;

import java.time.LocalDate;

import static com.fyodor.generators.Sampler.from;
import static com.google.common.collect.Range.*;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public final class LocalDateGeneratorTest {

    private final int sampleSize = 1000;

    @Test
    public void neverReturnsNull() {
        final Generator<LocalDate> generator = RDG.localDate(Range.all());
        assertThat(from(generator).sample(10000))
                .doesNotContainNull();
    }

    @Test
    public void closedRangeWhereLowerAndUpperBoundsAreBothTheSameShouldReturnTheSameDateEveryTime() {
        assertThat(from(RDG.localDate(closed(now(), now()))).sample(sampleSize).unique())
                .containsOnly(now());

        assertThat(from(RDG.localDate(closed(LocalDate.MIN, LocalDate.MIN))).sample(sampleSize).unique())
                .containsOnly(LocalDate.MIN);

        assertThat(from(RDG.localDate(closed(LocalDate.MAX, LocalDate.MAX))).sample(sampleSize).unique())
                .containsOnly(LocalDate.MAX);
    }

    @Test
    public void closedRangeWithOneSingleDayBetweenLowerBoundAndUpperBoundShouldReturnBothBounds() {
        assertThat(from(RDG.localDate(closed(LocalDate.MIN, LocalDate.MIN.plusDays(1)))).sample(sampleSize).unique())
                .containsOnly(LocalDate.MIN, LocalDate.MIN.plusDays(1));

        assertThat(from(RDG.localDate(closed(LocalDate.MAX.minusDays(1), LocalDate.MAX))).sample(sampleSize).unique())
                .containsOnly(LocalDate.MAX.minusDays(1), LocalDate.MAX);
    }

    @Test
    public void openRangeSpanningThreeDaysShouldReturnMiddleDateOnly() {
        final Generator<LocalDate> generator = RDG.localDate(open(now().minusDays(1), now().plusDays(1)));
        assertThat(from(generator).sample(sampleSize).unique()).containsOnly(now());
    }

    @Test
    public void openClosedRangeSpanningThreeDaysShouldReturnMiddleAndLatestDateOnly() {
        final Generator<LocalDate> generator = RDG.localDate(openClosed(now().minusDays(1), now().plusDays(1)));
        assertThat(from(generator).sample(sampleSize).unique())
                .containsOnly(now(), now().plusDays(1));
    }

    @Test
    public void closedOpenRangeSpanningThreeDaysShouldReturnEarliestAndMiddleDateOnly() {
        final Generator<LocalDate> generator = RDG.localDate(closedOpen(now().minusDays(1), now().plusDays(1)));
        assertThat(from(generator).sample(sampleSize).unique())
                .containsOnly(now().minusDays(1), now());
    }

    @Test
    public void greaterThanRangeOneDayBeforeDefaultUpperBoundReturnsOnlyTheDefaultUpperBound() {
        final LocalDate defaultUpperBound = LocalDate.of(2999, 12, 31);
        final Generator<LocalDate> generator = RDG.localDate(greaterThan(defaultUpperBound.minusDays(1)));
        assertThat(from(generator).sample(sampleSize).unique())
                .containsOnly(defaultUpperBound);
    }

    @Test
    public void lessThanRangeOneDayAfterDefaultLowerBoundReturnsOnlyTheDefaultLowerBound() {
        final LocalDate defaultLowerBound = LocalDate.of(0, 1, 1);
        final Generator<LocalDate> generator = RDG.localDate(lessThan(defaultLowerBound.plusDays(1)));
        assertThat(from(generator).sample(sampleSize).unique())
                .containsOnly(defaultLowerBound);
    }

    @Test
    public void atMostRangeForDefaultLowerBoundReturnsOnlyTheDefaultLowerBound() {
        final LocalDate defaultLowerBound = LocalDate.of(0, 1, 1);
        final Generator<LocalDate> generator = RDG.localDate(atMost(defaultLowerBound));
        assertThat(from(generator).sample(sampleSize).unique())
                .containsOnly(defaultLowerBound);
    }

    @Test
    public void atLeastRangeForDefaultUpperBoundReturnsOnlyTheDefaultUpperBound() {
        final LocalDate defaultUpperBound = LocalDate.of(2999, 12, 31);
        final Generator<LocalDate> generator = RDG.localDate(atLeast(defaultUpperBound));
        assertThat(from(generator).sample(sampleSize).unique())
                .containsOnly(defaultUpperBound);
    }
}
