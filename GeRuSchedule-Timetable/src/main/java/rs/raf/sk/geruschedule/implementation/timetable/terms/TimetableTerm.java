package rs.raf.sk.geruschedule.implementation.timetable.terms;

import rs.raf.sk.geruschedule.implementation.base.AbstractSchedule;
import rs.raf.sk.geruschedule.implementation.base.terms.AbstractScheduleTerm;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class TimetableTerm extends AbstractScheduleTerm {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public TimetableTerm(Location loc, LocalDate day, LocalTime startTime, LocalTime endTime, AbstractSchedule.TermManager termManager) {
        super(termManager);
        Objects.requireNonNull(loc);
        setLocation(loc);
        this.startTime = LocalDateTime.of(day, startTime);
        this.endTime = LocalDateTime.of(day, endTime);
    }

    @Override
    public boolean timeOccupied(LocalDateTime from, LocalDateTime to) {
        return (from.isEqual(startTime) || from.isEqual(endTime) || to.isEqual(startTime) || to.isEqual(endTime)) ||
               (from.isAfter(startTime) && from.isBefore(endTime)) ||
               (to.isAfter(startTime) && to.isBefore(endTime)) ||
               (from.isBefore(startTime) && to.isAfter(endTime));
    }

    @Override
    public boolean termIntersects(Term other) {
        return other.timeOccupied(startTime, endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimetableTerm that = (TimetableTerm) o;
        return getLocation().getName().compareTo(that.getLocation().getName()) == 0 && startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getLocation().getName() + " " + startTime.toString() + " - " + endTime.toString() + " : ");
        attributes.forEach((k, v) -> builder.append(k).append(":").append(v).append(" "));
        return builder.toString();
    }

    @Override
    public DayOfWeek getWeekday() {
        return startTime.getDayOfWeek();
    }

    @Override
    public LocalTime getStartTime() {
        return startTime.toLocalTime();
    }

    @Override
    public LocalTime getEndTime() {
        return endTime.toLocalTime();
    }
}
