package rs.raf.sk.geruschedule.specification.attributes;

/**
 * Describes a bound attribute's type.
 */
public enum AttributeType {
    /**
     * The attribute is represented by an arbitrary integer.
     */
    NUMERIC,

    /**
     * The attribute is represented by an arbitrary string.
     */
    STRING,

    /**
     * The attribute is represented by an element from a finite set of string values.
     */
    CHOOSE_ONE,

    /**
     * The attribute is represented by a subset of a finite set of string values.
     */
    CHOOSE_MANY
}
