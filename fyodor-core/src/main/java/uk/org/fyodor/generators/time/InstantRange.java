package uk.org.fyodor.generators.time;

import uk.org.fyodor.range.Range;

import java.time.*;

public final class InstantRange extends Range<Instant> {

    private InstantRange(final Instant lowerBound, final Instant upperBound) {
        super(lowerBound, upperBound);
    }

    public static InstantRange all() {
        return new InstantRange(Instant.MIN, Instant.MAX);
    }

    public static InstantRange now() {
        final Instant now = Timekeeper.instant();
        return new InstantRange(now, now);
    }

    public static InstantRange today() {
        return atDate(Timekeeper.today());
    }

    public static InstantRange atDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("range at date cannot be null");
        }

        final Instant startOfDayAtDate = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        final ZonedDateTime dateAndTime = startOfDayAtDate.atZone(ZoneId.systemDefault());
        final Instant startOfDay = dateAndTime.withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
        final Instant endOfDay = dateAndTime.withHour(23).withMinute(59).withSecond(59).withNano(0).toInstant();
        return new InstantRange(startOfDay, endOfDay);
    }

    public static InstantRange after(final Instant after) {
        if (after == null) {
            throw new IllegalArgumentException("range after instant cannot be null");
        }

        return new InstantRange(after.plusMillis(1), Instant.MAX);
    }

    public static InstantRange before(final Instant before) {
        if (before == null) {
            throw new IllegalArgumentException("range before instant cannot be null");
        }

        return new InstantRange(Instant.MIN, before.minusMillis(1));
    }

    public static InstantRange inTheFuture() {
        final Instant now = Timekeeper.instant();

        if (now.equals(Instant.MAX)) {
            throw new IllegalArgumentException("range cannot be in the future because today is the max instant");
        }

        return new InstantRange(now.plusSeconds(1), Instant.MAX);
    }

    public static InstantRange inThePast() {
        final Instant now = Timekeeper.instant();

        if (now.equals(Instant.MIN)) {
            throw new IllegalArgumentException("range cannot be in the past because today is the min instant");
        }

        return new InstantRange(Instant.MIN, now.minusSeconds(1));
    }
}
