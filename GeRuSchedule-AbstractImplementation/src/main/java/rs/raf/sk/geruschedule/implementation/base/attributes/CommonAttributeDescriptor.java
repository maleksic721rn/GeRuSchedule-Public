package rs.raf.sk.geruschedule.implementation.base.attributes;

import rs.raf.sk.geruschedule.specification.attributes.AttributeDescriptor;
import rs.raf.sk.geruschedule.specification.attributes.AttributeType;

public abstract class CommonAttributeDescriptor<T> implements AttributeDescriptor<T> {
    protected String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract AttributeType getType();

    @Override
    public abstract T getDefaultValue();

    @Override
    public abstract void addChoiceSetValue(String value);

    @Override
    public abstract void removeChoiceSetValue(String value);

    @Override
    public abstract boolean isValidValue(Object value);
}
