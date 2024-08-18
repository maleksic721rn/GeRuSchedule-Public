package rs.raf.sk.geruschedule.implementation.base.attributes;

import rs.raf.sk.geruschedule.specification.attributes.AttributeType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChooseOneAttributeDescriptor extends CommonAttributeDescriptor<String> {
    private final String defaultValue;
    private final Set<String> choiceSet = new HashSet<>();

    public ChooseOneAttributeDescriptor(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = Objects.requireNonNullElse(defaultValue, "");
        addChoiceSetValue(defaultValue);
    }

    @Override
    public AttributeType getType() {
        return AttributeType.CHOOSE_ONE;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Set<String> getChoiceSet() {
        return choiceSet;
    }

    @Override
    public void addChoiceSetValue(String value) {
        choiceSet.add(value);
    }

    @Override
    public void removeChoiceSetValue(String value) throws IllegalArgumentException {
        if(getDefaultValue().compareTo(value) == 0)
            throw new IllegalArgumentException("Set value " + value + " for attribute " + getName() + " cannot be removed as it is equal to the default value.");
        choiceSet.remove(value);
    }

    @Override
    public boolean isValidValue(Object value) {
        return choiceSet.contains(value.toString());
    }
}
