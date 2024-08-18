package rs.raf.sk.geruschedule.specification;

import rs.raf.sk.geruschedule.specification.attributes.AttributeComparison;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparisonType;
import rs.raf.sk.geruschedule.specification.attributes.AttributeType;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * An interface used for describing a schedule which holds information about specific events designated to be held at specific times in specific places.
 *
 * @see Term
 * @see Location
 */
public interface Schedule {
    /**
     * Binds an attribute for describing terms.
     * @param name The name of the attribute. Must be unique.
     * @param type The attribute's type.
     * @param defaultValue The attribute's default value.
     *                     If there are already terms present within the schedule, each of them will have the corresponding attribute set for them with this value.
     * @throws IllegalArgumentException An attribute with this name has already been bound.
     */
    void addTermAttribute(String name, AttributeType type, Object defaultValue) throws IllegalArgumentException;

    /**
     * Binds an attribute for describing locations.
     * @param name The name of the attribute. Must be unique.
     * @param type The attribute's type.
     * @param defaultValue The attribute's default value.
     *                     If there are already locations present within the schedule, each of them will have the corresponding attribute set for them with this value.
     * @throws IllegalArgumentException An attribute with this name has already been bound.
     */
    void addLocationAttribute(String name, AttributeType type, Object defaultValue) throws IllegalArgumentException;

    /**
     * Adds an entry to the set of valid values that a term attribute can choose from.
     * @param name The name of the corresponding attribute.
     *             There must already exist a term attribute with this name.
     * @param value The value to be added into the set.
     */
    void addTermAttributeSetValue(String name, String value);

    /**
     * Adds an entry to the set of valid values that a location attribute can choose from.
     * @param name The name of the corresponding attribute.
     *             There must already exist a location attribute with this name.
     * @param value The value to be added into the set.
     */
    void addLocationAttributeSetValue(String name, String value);

    /**
     * Unbinds an attribute used for describing terms.
     * @param name The name of the attribute.
     */
    void removeTermAttribute(String name);

    /**
     * Unbinds an attribute used for describing locations.
     * @param name The name of the attribute.
     */
    void removeLocationAttribute(String name);

    /**
     * Removes an entry from the set of valid values that a term attribute can choose from.
     * If any terms have an attribute equal to or containing the given value, it is removed/reset to the given attribute's default value for those terms.
     * @param name The name of the corresponding attribute.
     * @param value The value to remove.
     */
    void removeTermAttributeSetValue(String name, String value);

    /**
     * Removes an entry from the set of valid values that a location attribute can choose from.
     * If any locations have an attribute equal to or containing the given value, it is removed/reset to the given attribute's default value for those locations.
     * @param name The name of the corresponding attribute.
     * @param value The value to remove.
     */
    void removeLocationAttributeSetValue(String name, String value);

    /**
     * Adds a new location to the list of possible locations for terms.
     * @param name The name of the location.
     * @throws IllegalArgumentException A location with the given name already exists.
     */
    void addLocation(String name) throws IllegalArgumentException;

    /**
     * @param name The name of the requested location.
     * @return Whether a location with the given name exists is defined for the given schedule.
     */
    boolean locationExists(String name);

    /**
     * @param name The name of the requested location.
     * @return The object associated with the given location. If no such location exists, returns null.
     */
    Location getLocation(String name);

    /**
     * Removes a location.
     * @param name The name of the location to remove.
     *
     * @throws IllegalArgumentException There are terms which occupy the given location.
     */
    void removeLocation(String name) throws IllegalArgumentException;

    /**
     * Sets the value of an attribute for a location with the given name.
     * @param locName The name of the location.
     * @param attrName The name of the attribute.
     * @param value The attribute's new value.
     * @throws IllegalArgumentException No location exists with the given name, no attribute with the given name is bound, or the given value is invalid for the bound attribute.
     */
    void setLocationAttributeValue(String locName, String attrName, Object value) throws IllegalArgumentException;

    /**
     * Sets the value of an attribute for a given term.
     * @param term The term to set the value for.
     * @param attrName The name of the attribute.
     * @param value The attribute's new value.
     * @throws IllegalArgumentException No attribute with the given name is bound, or the given value is invalid for the bound attribute.
     */
    void setTermAttributeValue(Term term, String attrName, Object value) throws IllegalArgumentException;

    /**
     * Adds a new term for the given location at the given time.
     * @param loc The location where the term is held.
     * @param startDay The first day during which the term may be held.
     * @param endDay The last day during which the term may be held.
     * @param startTime The term's starting time.
     * @param endTime The term's ending time.
     * @param day The day of the week on which the term is held.
     * @return The new term.
     * @throws IllegalArgumentException The given location is already occupied at the given time.
     * @throws UnsupportedOperationException The given term parameters are invalid for the given type of schedule.
     */
    Term addTerm(Location loc, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * A version of {@link Schedule#addTerm(Location, LocalDate, LocalDate, LocalTime, LocalTime, DayOfWeek)} used for a single day.
     *
     * @see Schedule#addTerm(Location, LocalDate, LocalDate, LocalTime, LocalTime, DayOfWeek)
     */
    Term addTerm(Location loc, LocalDate day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * Removes a term.
     * @param term The term to remove.
     */
    void removeTerm(Term term);

    /**
     * Moves a term to a new time frame, maintaining its location and attributes.
     * @param term The term to move.
     * @param startDay The new first day during which the term may be held.
     * @param endDay The new last day during which the term may be held.
     * @param startTime The term's starting time.
     * @param endTime The term's new ending time.
     * @param day The new day of the week on which the term is held.
     * @return The given term with its new parameters.
     * @throws IllegalArgumentException The term's location is occupied during the given time. If this is thrown, no changes are made to the given term.
     * @throws UnsupportedOperationException The given term parameters are invalid for the given type of schedule.
     */
    Term moveTerm(Term term, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * A version of {@link Schedule#moveTerm(Term, LocalDate, LocalDate, LocalTime, LocalTime, DayOfWeek)} used for a single day.
     *
     * @see Schedule#moveTerm(Term, LocalDate, LocalDate, LocalTime, LocalTime, DayOfWeek)
     */
    Term moveTerm(Term term, LocalDate day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * Changes the location of an existing term.
     * @param term The term to change the location of.
     * @param loc The new location.
     * @throws IllegalArgumentException The given location is occupied at the time when the given term is supposed to be held.
     */
    void setTermLocation(Term term, Location loc) throws IllegalArgumentException;

    /**
     * @param locName A pattern for searching location names.
     * @param startTime The beginning of the time span to search through.
     * @param endTime The end of the time span to search through.
     * @param locComparisons A set of comparisons to make for location attributes.
     * @param termComparisons A set of comparisons to make for term attributes.
     * @return A list of terms matching a specific set of search criteria.
     * @throws UnsupportedOperationException One of the search criteria cannot be applied for its corresponding attribute's type.
     */
    List<Term> doSearch(String locName, LocalDateTime startTime, LocalDateTime endTime, List<AttributeComparison> locComparisons, List<AttributeComparison> termComparisons) throws UnsupportedOperationException;

    /**
     * Marks a day during which terms cannot be held.
     * @param day The day to mark.
     * @throws UnsupportedOperationException The given schedule does not support exempt days.
     */
    void addExemptDay(DayOfWeek day) throws UnsupportedOperationException;

    /**
     * Unmarks a day during which terms cannot be held.
     * @param day The day to unmark.
     * @throws UnsupportedOperationException The given schedule does not support exempt days.
     */
    void removeExemptDay(DayOfWeek day) throws UnsupportedOperationException;

    /**
     * @param day The day to check.
     * @return Whether terms cannot be held during the given day.
     */
    boolean isExemptDay(DayOfWeek day);

    /**
     * @param name The attribute's name.
     * @return The type of the location attribute in question.
     * @throws IllegalArgumentException No location attribute with the given name is bound.
     *
     * @see AttributeType
     */
    AttributeType getLocationAttributeType(String name) throws IllegalArgumentException;

    /**
     * @param name The attribute's name.
     * @return The type of the term attribute in question.
     * @throws IllegalArgumentException No term attribute with the given name is bound.
     *
     * @see AttributeType
     */
    AttributeType getTermAttributeType(String name) throws IllegalArgumentException;

    /**
     * @param attrName The name of the location attribute to compare.
     * @param compType The type of comparison to make.
     * @param val The value to compare against.
     * @param disj Whether the next comparison in a list should not be logically conjunctive.
     * @return A new {@link AttributeComparison} made from the given parameters.
     * @throws IllegalArgumentException No location attribute with the given name is bound.
     */
    AttributeComparison makeLocationComparison(String attrName, AttributeComparisonType compType, Object val, boolean disj) throws IllegalArgumentException;

    /**
     * @param attrName The name of the term attribute to compare.
     * @param compType The type of comparison to make.
     * @param val The value to compare against.
     * @param disj Whether the next comparison in a list should not be logically conjunctive.
     * @return A new {@link AttributeComparison} made from the given parameters.
     * @throws IllegalArgumentException No term attribute with the given name is bound.
     */
    AttributeComparison makeTermComparison(String attrName, AttributeComparisonType compType, Object val, boolean disj) throws IllegalArgumentException;
}