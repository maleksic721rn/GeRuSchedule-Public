package rs.raf.sk.geruschedule.specification.terms;

import rs.raf.sk.geruschedule.specification.AttributableScheduleItem;
import rs.raf.sk.geruschedule.specification.locations.Location;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * An interface used for describing events which take place in certain {@link Location locations} during certain times.
 *
 * @see Location
 * @see rs.raf.sk.geruschedule.specification.Schedule Schedule
 */
public interface Term extends AttributableScheduleItem {
    /**
     * @return The {@link Location location} which the given term occupies.
     *
     * @see Location
     */
    Location getLocation();

    /**
     * @param from The beginning of the interval.
     * @param to The end of the interval.
     * @return Whether this term is ongoing during a given range of time.
     */
    boolean timeOccupied(LocalDateTime from, LocalDateTime to);

    /**
     * @param other The term being compared against.
     * @return Whether the given term's time range intersects with that of another term.
     *         This method functions independently of location, so it cannot be used to gauge whether two terms can actually coexist or not.
     */
    boolean termIntersects(Term other);

    /**
     * @return The day of the week which the given term is held during.
     */
    DayOfWeek getWeekday();

    /**
     * @return The time when the given term starts.
     */
    LocalTime getStartTime();

    /**
     * @return The time when the given term ends.
     */
    LocalTime getEndTime();

    /**
     * Compares the given term with another term in relation to which one is "first".
     * @param other The term to compare with.
     * @param weekday If true, the terms will be compared by their weekday first, before being compared by their time.
     * @return An integer representing the order in which the two terms are to be arranged (-1, 0 or 1).
     */
    int compare(Term other, boolean weekday);
}
