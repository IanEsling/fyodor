package uk.org.fyodor.generators.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public final class Timekeeper {

    private static final ThreadLocal<Clock> clock = ThreadLocal.withInitial(Clock::systemDefaultZone);

    private Timekeeper() {
    }

    public static void from(final Clock clock) {
        Timekeeper.clock.set(clock);
    }

    static LocalDate today() {
        return instant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    static Instant instant() {
        return clock.get().instant();
    }

}
