package rs.raf.sk.geruschedule.implementation.base.attributes;

import rs.raf.sk.geruschedule.specification.attributes.AttributeComparison;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparisonType;
import rs.raf.sk.geruschedule.specification.attributes.AttributeDescriptor;

import java.util.Set;

public class CommonAttributeComparison implements AttributeComparison {
    private final AttributeDescriptor<?> descriptor;
    private final AttributeComparisonType compType;
    private final Object compValue;
    private final boolean disjunctive;

    public CommonAttributeComparison(AttributeDescriptor<?> descriptor, AttributeComparisonType compType, Object compValue, boolean disjunctive) {
        this.descriptor = descriptor;
        this.compType = compType;
        this.compValue = compValue;
        this.disjunctive = disjunctive;
    }

    @Override
    public AttributeDescriptor<?> getDescriptor() {
        return descriptor;
    }

    @Override
    public AttributeComparisonType getComparisonType() {
        return compType;
    }

    @Override
    public Object getComparisonValue() {
        return compValue;
    }

    @Override
    public boolean nextDisjunction() {
        return disjunctive;
    }

    @Override
    public boolean doCompare(Object other) throws UnsupportedOperationException {
        Object val1 = getComparisonValue();
        UnsupportedOperationException err = new UnsupportedOperationException("The operation '" + getComparisonType().toString() + "' cannot be applied for the attribute '" + getDescriptor().getName() + "'.");
        switch (getDescriptor().getType()) {
            case NUMERIC -> {
                switch (getComparisonType()) {
                    case EQUAL -> {
                        return (int) other == (int) val1;
                    }
                    case LESSER -> {
                        return (int) other < (int) val1;
                    }
                    case GREATER -> {
                        return (int) other > (int) val1;
                    }
                    case NOT_EQUAL -> {
                        return (int) other != (int) val1;
                    }
                    case LESSER_EQUAL -> {
                        return (int) other <= (int) val1;
                    }
                    case GREATER_EQUAL -> {
                        return (int) other >= (int) val1;
                    }
                    default -> throw err;
                }
            }
            case STRING, CHOOSE_ONE -> {
                switch (getComparisonType()) {
                    case EQUAL -> {
                        return other.toString().compareTo(val1.toString()) == 0;
                    }
                    case NOT_EQUAL -> {
                        return other.toString().compareTo(val1.toString()) != 0;
                    }
                    case CONTAINS -> {
                        return other.toString().contains(val1.toString());
                    }
                    default -> throw err;
                }
            }
            case CHOOSE_MANY -> {
                switch (getComparisonType()) {
                    case HAS_ANY -> {
                        return ((Set<String>) val1).stream().anyMatch(x -> ((Set<String>) other).contains(x));
                    }
                    case HAS_ALL -> {
                        return ((Set<String>) other).containsAll((Set<String>) val1);
                    }
                    default -> throw err;
                }
            }
        }
        return false;
    }
}
