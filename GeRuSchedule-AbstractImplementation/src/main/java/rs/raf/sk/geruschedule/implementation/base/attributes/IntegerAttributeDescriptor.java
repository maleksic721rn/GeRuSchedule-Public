package rs.raf.sk.geruschedule.implementation.base.attributes;

import rs.raf.sk.geruschedule.specification.attributes.AttributeType;

import java.util.Objects;
import java.util.Set;

public class IntegerAttributeDescriptor extends CommonAttributeDescriptor<Integer> {
    private final Integer defaultValue;

    public IntegerAttributeDescriptor(String name, Integer defaultValue) {
        this.name = name;
        this.defaultValue = Objects.requireNonNullElse(defaultValue, 0);
    }

    @Override
    public AttributeType getType() {
        return AttributeType.NUMERIC;
    }

    @Override
    public Integer getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Set<String> getChoiceSet() {
        return null;
    }

    @Override
    public void addChoiceSetValue(String value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Invalid operation for attribute '" + getName() + "'.");
    }

    @Override
    public void removeChoiceSetValue(String value) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Invalid operation for attribute '" + getName() + "'.");
    }

    @Override
    public boolean isValidValue(Object value) {
        return true;
    }
}
