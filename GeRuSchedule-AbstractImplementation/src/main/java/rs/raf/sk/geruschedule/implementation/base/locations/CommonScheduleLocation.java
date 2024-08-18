package rs.raf.sk.geruschedule.implementation.base.locations;

import rs.raf.sk.geruschedule.implementation.base.AbstractSchedule;
import rs.raf.sk.geruschedule.specification.locations.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommonScheduleLocation implements Location {
    private final String name;
    private final Map<String, Object> attributes = new HashMap<>();

    public CommonScheduleLocation(String name, AbstractSchedule.LocationManager locManager) {
        Objects.requireNonNull(locManager);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getAttributeValue(String attrName) {
        return attributes.get(attrName);
    }

    @Override
    public boolean attributeIsDefined(String attrName) {
        return attributes.containsKey(attrName);
    }

    public void setAttributeValue(String attrName, Object value, AbstractSchedule.LocationManager locManager) {
        Objects.requireNonNull(locManager);
        attributes.put(attrName, value);
    }

    public void removeAttribute(String attrName, AbstractSchedule.LocationManager locManager) {
        Objects.requireNonNull(locManager);
        attributes.remove(attrName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getName() + ": ");
        attributes.forEach((k, v) -> builder.append(k).append(":").append(v).append(" "));
        return builder.toString();
    }
}
