package rs.raf.sk.geruschedule.implementation.base.attributes;

import rs.raf.sk.geruschedule.specification.attributes.AttributeType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ChooseManyAttributeDescriptor extends CommonAttributeDescriptor<Set<String>> {
    private final Set<String> defaultValue;
    private final Set<String> choiceSet = new HashSet<>();

    public ChooseManyAttributeDescriptor(String name, Set<String> defaultValue) {
        this.name = name;
        this.defaultValue = Objects.requireNonNullElseGet(defaultValue, HashSet::new);
        defaultValue.forEach(this::addChoiceSetValue);
    }

    @Override
    public AttributeType getType() {
        return AttributeType.CHOOSE_MANY;
    }

    @Override
    public Set<String> getDefaultValue() {
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
        if(getDefaultValue().contains(value))
            throw new IllegalArgumentException("Set value " + value + " for attribute " + getName() + " cannot be removed as it is part of the default value set.");
        choiceSet.remove(value);
    }

    @Override
    public boolean isValidValue(Object value) {
        return choiceSet.containsAll((Set<String>) value);
    }
}
