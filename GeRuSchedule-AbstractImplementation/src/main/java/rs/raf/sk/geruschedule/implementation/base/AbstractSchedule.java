package rs.raf.sk.geruschedule.implementation.base;

import rs.raf.sk.geruschedule.implementation.base.attributes.*;
import rs.raf.sk.geruschedule.implementation.base.locations.CommonScheduleLocation;
import rs.raf.sk.geruschedule.implementation.base.terms.AbstractScheduleTerm;
import rs.raf.sk.geruschedule.specification.AttributableScheduleItem;
import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparison;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparisonType;
import rs.raf.sk.geruschedule.specification.attributes.AttributeDescriptor;
import rs.raf.sk.geruschedule.specification.attributes.AttributeType;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public abstract class AbstractSchedule implements Schedule {
    public static final class LocationManager {
        private LocationManager() { }
    }
    protected static final LocationManager locationManager = new LocationManager();

    public static final class TermManager {
        private TermManager() { }
    }
    protected static final TermManager termManager = new TermManager();

    private final Set<DayOfWeek> exemptDays = new HashSet<>();
    protected final List<Term> terms = new ArrayList<>();
    protected final Map<String, CommonScheduleLocation> locations = new HashMap<>();
    private final Map<String, AttributeDescriptor<?>> locationAttributeDescriptors = new HashMap<>();
    private final Map<String, AttributeDescriptor<?>> termAttributeDescriptors = new HashMap<>();

    private void addAttributeDescriptor(String name, AttributeType type, Object defaultValue, Map<String, AttributeDescriptor<?>> map) {
        switch (type) {
            case NUMERIC -> map.put(name, new IntegerAttributeDescriptor(name, (Integer) defaultValue));
            case STRING -> map.put(name, new StringAttributeDescriptor(name, defaultValue.toString()));
            case CHOOSE_ONE -> map.put(name, new ChooseOneAttributeDescriptor(name, defaultValue.toString()));
            case CHOOSE_MANY -> map.put(name, new ChooseManyAttributeDescriptor(name, (Set<String>) defaultValue));
        }
    }

    private AttributeType getAttributeType(String name, Map<String, AttributeDescriptor<?>> map) {
        return map.get(name).getType();
    }

    @Override
    public AttributeType getLocationAttributeType(String name) throws IllegalArgumentException {
        if(!locationAttributeDescriptors.containsKey(name))
            throw new IllegalArgumentException("No location attribute with the name '" + name + "' exists.");
        return getAttributeType(name, locationAttributeDescriptors);
    }

    @Override
    public AttributeType getTermAttributeType(String name) throws IllegalArgumentException {
        if(!termAttributeDescriptors.containsKey(name))
            throw new IllegalArgumentException("No term attribute with the name '" + name + "' exists.");
        return getAttributeType(name, termAttributeDescriptors);
    }

    @Override
    public void addTermAttribute(String name, AttributeType type, Object defaultValue) throws IllegalArgumentException {
        if(termAttributeDescriptors.containsKey(name))
            throw new IllegalArgumentException("A term attribute with the name '" + name + "' is already defined.");
        addAttributeDescriptor(name, type, defaultValue, termAttributeDescriptors);
        for(Term term : terms) {
            if(!term.attributeIsDefined(name)) {
                ((AbstractScheduleTerm) term).setAttributeValue(name, defaultValue, termManager);
            }
        }
    }

    @Override
    public void addLocationAttribute(String name, AttributeType type, Object defaultValue) throws IllegalArgumentException {
        if(locationAttributeDescriptors.containsKey(name))
            throw new IllegalArgumentException("A location attribute with the name '" + name + "' is already defined.");
        addAttributeDescriptor(name, type, defaultValue, locationAttributeDescriptors);
        for(CommonScheduleLocation loc : locations.values()) {
            if(!loc.attributeIsDefined(name)) {
                loc.setAttributeValue(name, defaultValue, locationManager);
            }
        }
    }

    private void addAttributeSetValue(String name, String value, Map<String, AttributeDescriptor<?>> map) {
        map.get(name).addChoiceSetValue(value);
    }

    @Override
    public void addTermAttributeSetValue(String name, String value) {
        addAttributeSetValue(name, value, termAttributeDescriptors);
    }

    @Override
    public void addLocationAttributeSetValue(String name, String value) {
        addAttributeSetValue(name, value, locationAttributeDescriptors);
    }

    @Override
    public void removeTermAttribute(String name) {
        termAttributeDescriptors.remove(name);
        for(Term term : terms) {
            if(term.attributeIsDefined(name)) {
                ((AbstractScheduleTerm) term).removeAttribute(name, termManager);
            }
        }
    }

    @Override
    public void removeLocationAttribute(String name) {
        locationAttributeDescriptors.remove(name);
        for(CommonScheduleLocation loc : locations.values()) {
            if(loc.attributeIsDefined(name)) {
                loc.removeAttribute(name, locationManager);
            }
        }
    }

    private void removeAttributeSetValue(String name, String value, Map<String, AttributeDescriptor<?>> map) {
        map.get(name).removeChoiceSetValue(value);
    }

    @Override
    public void removeTermAttributeSetValue(String name, String value) {
        removeAttributeSetValue(name, value, termAttributeDescriptors);
        for(Term term : terms) {
            ((AbstractScheduleTerm) term).setAttributeValue(name, termAttributeDescriptors.get(name).getDefaultValue(), termManager);
        }
    }

    @Override
    public void removeLocationAttributeSetValue(String name, String value) {
        removeAttributeSetValue(name, value, locationAttributeDescriptors);
        for(CommonScheduleLocation loc : locations.values()) {
            switch(locationAttributeDescriptors.get(name).getType()) {
                case CHOOSE_ONE -> {
                    if(loc.getAttributeValue(name).toString().compareTo(value) == 0) {
                        loc.setAttributeValue(name, locationAttributeDescriptors.get(name).getDefaultValue(), locationManager);
                    }
                }
                case CHOOSE_MANY -> {
                    Set<String> valueSet = ((Set<String>) loc.getAttributeValue(name));
                    valueSet.remove(value);
                    if(valueSet.size() == 0)
                        loc.setAttributeValue(name, locationAttributeDescriptors.get(name).getDefaultValue(), locationManager);
                }
            }
        }
    }

    @Override
    public void addLocation(String name) throws IllegalArgumentException {
        if(locations.containsKey(name))
            throw new IllegalArgumentException("A location with the name '" + name + "' already exists.");
        locations.put(name, new CommonScheduleLocation(name, locationManager));
        for(AttributeDescriptor<?> a : locationAttributeDescriptors.values()) {
            locations.get(name).setAttributeValue(a.getName(), a.getDefaultValue(), locationManager);
        }
    }

    private boolean isValidAttributeValue(String attrName, Object value, Map<String, AttributeDescriptor<?>> map) {
        return map.get(attrName).isValidValue(value);
    }

    @Override
    public boolean locationExists(String name) {
        return locations.containsKey(name);
    }

    @Override
    public Location getLocation(String name) {
        return locations.get(name);
    }

    @Override
    public void removeLocation(String name) throws IllegalArgumentException {
        if(terms.stream().anyMatch(x -> x.getLocation().getName().compareTo(name) == 0))
            throw new IllegalArgumentException("The location cannot be removed because there are terms that occupy it.");
        locations.remove(name);
    }

    @Override
    public void setLocationAttributeValue(String locName, String attrName, Object value) throws IllegalArgumentException {
        if(!locationExists(locName))
            throw new IllegalArgumentException("No location with the name '" + locName + "' exists.");
        if(!getLocation(locName).attributeIsDefined(attrName))
            throw new IllegalArgumentException("No location attribute with the name '" + attrName + "' is bound to '" + locName + "'.");
        if(!isValidAttributeValue(attrName, value, locationAttributeDescriptors))
            throw new IllegalArgumentException("The value '" + value.toString() + "' is not valid for the location attribute '" + attrName + "'.");
        ((CommonScheduleLocation) getLocation(locName)).setAttributeValue(attrName, value, locationManager);
    }

    @Override
    public void setTermAttributeValue(Term term, String attrName, Object value) throws IllegalArgumentException {
        if(!term.attributeIsDefined(attrName))
            throw new IllegalArgumentException("No term attribute with the name '" + attrName + "' is bound to the given term.");
        if(!isValidAttributeValue(attrName, value, termAttributeDescriptors))
            throw new IllegalArgumentException("The value '" + value.toString() + "' is not valid for the term attribute '" + attrName + "'.");
        ((AbstractScheduleTerm) term).setAttributeValue(attrName, value, termManager);
    }

    protected void checkTermInternal(Location loc, LocalDate startDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException {
        int weekDiff = day.getValue() - startDay.getDayOfWeek().getValue();
        if (weekDiff < 0)
            weekDiff += 7;
        LocalDateTime firstTermStart = LocalDateTime.of(startDay.plusDays(weekDiff), startTime);
        LocalDateTime firstTermEnd = LocalDateTime.of(startDay.plusDays(weekDiff), endTime);
        if (isExemptDay(day)) {
            throw new IllegalArgumentException("The given day '" + day + "' is an exempt day.");
        }
        for(Term term : terms) {
            if (term.getLocation().getName().compareTo(loc.getName()) == 0 && (term.timeOccupied(firstTermStart, firstTermEnd))) {
                throw new IllegalArgumentException("The given location '" + loc.getName() + "' is occupied during the given time '" + startTime + "' on " + day + ".");
            }
        }
    }

    protected void setTermDefaults(Term term) {
        for(AttributeDescriptor<?> a : termAttributeDescriptors.values()) {
            ((AbstractScheduleTerm) term).setAttributeValue(a.getName(), a.getDefaultValue(), termManager);
        }
    }

    protected void copyTermAttributes(Term src, Term dst) {
        for(AttributeDescriptor<?> a : termAttributeDescriptors.values()) {
            ((AbstractScheduleTerm) dst).setAttributeValue(a.getName(), src.getAttributeValue(a.getName()), termManager);
        }
    }

    @Override
    public abstract Term addTerm(Location loc, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException;
    @Override
    public abstract Term addTerm(Location loc, LocalDate day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, UnsupportedOperationException;

    @Override
    public Term moveTerm(Term term, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException {
        removeTerm(term);
        Term t;
        try {
            t = addTerm(term.getLocation(), startDay, endDay, startTime, endTime, day);
        } catch(IllegalArgumentException | UnsupportedOperationException e) {
            terms.add(term);
            throw e;
        }
        copyTermAttributes(term, t);
        return t;
    }

    @Override
    public Term moveTerm(Term term, LocalDate day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, UnsupportedOperationException {
        return moveTerm(term, day, day, startTime, endTime, day.getDayOfWeek());
    }

    @Override
    public void removeTerm(Term term) {
        terms.remove(term);
    }

    @Override
    public void setTermLocation(Term term, Location loc) throws IllegalArgumentException {
        if(terms.stream().anyMatch(t -> t.getLocation().getName().compareTo(term.getLocation().getName()) == 0 && t.termIntersects(term)))
            throw new IllegalArgumentException("The location '" + loc.getName() + "' is already occupied during the given term.");
        ((AbstractScheduleTerm) term).setLocation(loc, termManager);
    }

    private AttributeComparison makeComparison(String name, AttributeComparisonType compType, Object val, boolean disj, Map<String, AttributeDescriptor<?>> map) {
        return new CommonAttributeComparison(map.get(name), compType, val, disj);
    }

    @Override
    public AttributeComparison makeLocationComparison(String name, AttributeComparisonType compType, Object val, boolean disj) {
        if(!locationAttributeDescriptors.containsKey(name))
            throw new IllegalArgumentException("No location attribute with the name '" + name + "' exists.");
        return makeComparison(name, compType, val, disj, locationAttributeDescriptors);
    }

    @Override
    public AttributeComparison makeTermComparison(String name, AttributeComparisonType compType, Object val, boolean disj) {
        if(!termAttributeDescriptors.containsKey(name))
            throw new IllegalArgumentException("No term attribute with the name '" + name + "' exists.");
        return makeComparison(name, compType, val, disj, termAttributeDescriptors);
    }

    private boolean allAttrCompare(AttributableScheduleItem item, List<AttributeComparison> comps) {
        boolean lastD = false;
        boolean ok = true;
        for (AttributeComparison comp : comps) {
            Object val0 = item.getAttributeValue(comp.getDescriptor().getName());
            boolean tmp = comp.doCompare(val0);
            if(lastD)
                ok = ok || tmp;
            else
                ok = ok && tmp;
            lastD = comp.nextDisjunction();
        }
        return ok;
    }

    @Override
    public List<Term> doSearch(String locName, LocalDateTime startTime, LocalDateTime endTime, List<AttributeComparison> locComparisons, List<AttributeComparison> termComparisons) throws UnsupportedOperationException {
        Map<String, Boolean> locCache = new HashMap<>();
        List<Term> res = new ArrayList<>();

        for(Term term : terms) {
            if(term.timeOccupied(startTime, endTime) && (locName.length() == 0 || term.getLocation().getName().contains(locName))) {
                if(locCache.get(term.getLocation().getName()) == null) {
                    locCache.put(term.getLocation().getName(), allAttrCompare(term.getLocation(), locComparisons));
                }

                if(locCache.get(term.getLocation().getName())) {
                    if(allAttrCompare(term, termComparisons)) {
                        res.add(term);
                    }
                }
            }
        }

        return res;
    }

    @Override
    public void addExemptDay(DayOfWeek day) throws UnsupportedOperationException {
        exemptDays.add(day);
    }
    @Override
    public void removeExemptDay(DayOfWeek day) throws UnsupportedOperationException {
        exemptDays.remove(day);
    }
    @Override
    public boolean isExemptDay(DayOfWeek day) {
        return exemptDays.contains(day);
    }
}
