package rs.raf.sk.geruschedule.specification;

/**
 * An interface for an item contained within a {@link Schedule schedule} which can have specific attributes with different values bound to it.
 * The attributes are only stored as values and are meant to be described in the schedule which stores the given object.
 *
 * @see rs.raf.sk.geruschedule.specification.attributes.AttributeDescriptor AttributeDescriptor
 */
public interface AttributableScheduleItem {
    /**
     * @param attrName The name of the requested attribute.
     * @return The value of the requested attribute, or null if no such attribute exists.
     */
    Object getAttributeValue(String attrName);

    /**
     * @param attrName The name of the requested attribute.
     * @return Whether this attribute is defined for the given object.
     */
    boolean attributeIsDefined(String attrName);
}
