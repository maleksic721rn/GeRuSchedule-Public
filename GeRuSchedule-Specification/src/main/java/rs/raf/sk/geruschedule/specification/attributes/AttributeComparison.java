package rs.raf.sk.geruschedule.specification.attributes;

/**
 * An interface used for making comparisons between a bound attribute and an arbitrary object.
 * It's implementations are intended for use in a list of comparisons made to filter data from a schedule.
 */
public interface AttributeComparison {
    /**
     * @return The {@link AttributeDescriptor descriptor} for the attribute being compared.
     *
     * @see AttributeDescriptor
     */
    AttributeDescriptor<?> getDescriptor();

    /**
     * @return The type of comparison to make.
     *
     * @see AttributeComparisonType
     */
    AttributeComparisonType getComparisonType();

    /**
     * @return The object which the attribute is being compared against.
     */
    Object getComparisonValue();

    /**
     * @return Whether the next comparison in a chain is going to be conjunctive or disjunctive.
     */
    boolean nextDisjunction();

    /**
     * @param other The value to check.
     * @return Whether the given value matches the given comparison's criteria.
     * @throws UnsupportedOperationException The given comparison is invalid for the given type of attribute.
     */
    boolean doCompare(Object other) throws UnsupportedOperationException;
}
