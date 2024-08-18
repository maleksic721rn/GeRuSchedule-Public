package rs.raf.sk.geruschedule.implementation.base.terms;

import rs.raf.sk.geruschedule.implementation.base.AbstractSchedule;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractScheduleTerm implements Term {
    private Location loc;
    protected final Map<String, Object> attributes = new HashMap<>();

    public AbstractScheduleTerm(AbstractSchedule.TermManager termManager) {
        Objects.requireNonNull(termManager);
    }

    @Override
    public Location getLocation() {
        return loc;
    }

    protected void setLocation(Location location) {
        loc = location;
    }

    @Override
    public Object getAttributeValue(String attrName) {
        return attributes.get(attrName);
    }

    @Override
    public boolean attributeIsDefined(String attrName) {
        return attributes.containsKey(attrName);
    }

    @Override
    public abstract boolean timeOccupied(LocalDateTime from, LocalDateTime to);

    @Override
    public abstract boolean termIntersects(Term other);

    @Override
    public abstract boolean equals(Object that);

    @Override
    public int compare(Term other, boolean weekday) {
        if (weekday) {
            int comp = getWeekday().compareTo(other.getWeekday());
            if (comp != 0)
                return comp;
        }
        int comp = getStartTime().compareTo(other.getStartTime());
        if (comp != 0)
            return comp;
        return getEndTime().compareTo(other.getEndTime());
    }

    public void setAttributeValue(String attrName, Object value, AbstractSchedule.TermManager termManager) {
        Objects.requireNonNull(termManager);
        attributes.put(attrName, value);
    }

    public void removeAttribute(String attrName, AbstractSchedule.TermManager termManager) {
        Objects.requireNonNull(termManager);
        attributes.remove(attrName);
    }

    public void setLocation(Location loc, AbstractSchedule.TermManager termManager) {
        Objects.requireNonNull(termManager);
        this.loc = loc;
    }
}
