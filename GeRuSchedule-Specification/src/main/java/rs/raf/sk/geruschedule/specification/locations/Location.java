package rs.raf.sk.geruschedule.specification.locations;

import rs.raf.sk.geruschedule.specification.AttributableScheduleItem;

/**
 * An interface used for describing physical locations which terms take place in.
 * By default, a location's name must be unique within one schedule, and multiple terms cannot be held at once in the same location; but this is enforced within the schedule itself and not a location's implementation(s).
 *
 * @see rs.raf.sk.geruschedule.specification.Schedule Schedule
 */
public interface Location extends AttributableScheduleItem {
    /**
     * @return The name of the given location.
     */
    String getName();
}
