package uk.org.fyodor.generators.time;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalTime;

import static uk.org.fyodor.generators.time.LocalTimeRange.after;
import static uk.org.fyodor.generators.time.LocalTimeRange.before;

public final class LocalTimeRangeTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void cannotBeBeforeNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("range cannot be before null");

        before(null);
    }

    @Test
    public void cannotBeBeforeMinimumTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("range cannot be before the minimum time");

        before(LocalTime.MIN);
    }

    @Test
    public void cannotBeAfterNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("range cannot be after null");

        after(null);
    }

    @Test
    public void cannotBeAfterMaximumTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("range cannot be after the maximum time");

        after(LocalTime.MAX);
    }
}