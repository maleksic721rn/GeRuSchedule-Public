package rs.raf.sk.geruschedule.specification.attributes;

import java.util.Set;

/**
 * An interface used for describing a bound attribute. These objects do not hold any concrete values for the attributes they are describing.
 * @param <T> The internal data type which the described attribute uses to store a value.
 */
public interface AttributeDescriptor<T> {
    /**
     * @return The name of a given attribute.
     */
    String getName();

    /**
     * @return The given attribute's type.
     *
     * @see AttributeType
     */
    AttributeType getType();

    /**
     * @return The default value for a given attribute.
     */
    T getDefaultValue();

    /**
     * @return The set of possible values for the given attribute, or null if not applicable.
     */
    Set<String> getChoiceSet();

    /**
     * Adds an entry to the set of possible values for the given attribute.
     * @param value The value to add.
     * @throws UnsupportedOperationException The operation is invalid for the given type of attribute.
     */
    void addChoiceSetValue(String value) throws UnsupportedOperationException;

    /**
     * Removes an entry from the set of possible values for the given attribute.
     * @param value The value to remove.
     * @throws IllegalArgumentException The given value is part of the attribute's default value.
     * @throws UnsupportedOperationException The operation is invalid for the given type of attribute.
     */
    void removeChoiceSetValue(String value) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * @param value The value to test.
     * @return Whether the given value is valid for the given attribute.
     */
    boolean isValidValue(Object value);
}