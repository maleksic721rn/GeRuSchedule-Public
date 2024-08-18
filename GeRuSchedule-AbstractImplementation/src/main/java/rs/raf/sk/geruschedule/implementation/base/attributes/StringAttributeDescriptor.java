package rs.raf.sk.geruschedule.implementation.base.attributes;

import rs.raf.sk.geruschedule.specification.attributes.AttributeType;

import java.util.Objects;
import java.util.Set;

public class StringAttributeDescriptor extends CommonAttributeDescriptor<String> {
    private final String defaultValue;

    public StringAttributeDescriptor(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = Objects.requireNonNullElse(defaultValue, "");
    }

    @Override
    public AttributeType getType() {
        return AttributeType.STRING;
    }

    @Override
    public String getDefaultValue() {
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
