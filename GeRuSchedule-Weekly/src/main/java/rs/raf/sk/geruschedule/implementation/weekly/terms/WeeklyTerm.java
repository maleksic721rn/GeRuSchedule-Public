package rs.raf.sk.geruschedule.implementation.weekly.terms;

import rs.raf.sk.geruschedule.implementation.base.AbstractSchedule;
import rs.raf.sk.geruschedule.implementation.base.terms.AbstractScheduleTerm;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class WeeklyTerm extends AbstractScheduleTerm {
    private final LocalDate startDay;
    private final LocalDate endDay;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final DayOfWeek day;

    public WeeklyTerm(Location loc, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day, AbstractSchedule.TermManager termManager) {
        super(termManager);
        Objects.requireNonNull(loc);
        setLocation(loc);
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    @Override
    public boolean timeOccupied(LocalDateTime from, LocalDateTime to) {
        int weekDiff = day.getValue() - startDay.getDayOfWeek().getValue();
        if (weekDiff < 0)
            weekDiff += 7;
        LocalDateTime firstTermStart = LocalDateTime.of(startDay.plusDays(weekDiff), startTime);
        LocalDateTime firstTermEnd = LocalDateTime.of(startDay.plusDays(weekDiff), endTime);
        LocalDateTime termEnd = LocalDateTime.of(endDay, endTime);
        if(endTime.isBefore(startTime)) {
            firstTermEnd = firstTermEnd.plusDays(1);
            termEnd = termEnd.plusDays(1);
        }
        while(firstTermEnd.isBefore(termEnd) || firstTermEnd.isEqual(termEnd)) {
            if((from.isEqual(firstTermStart) || from.isEqual(firstTermEnd) || to.isEqual(firstTermStart) || to.isEqual(firstTermEnd)) ||
                    (from.isAfter(firstTermStart) && from.isBefore(firstTermEnd)) ||
                    (to.isAfter(firstTermStart) && to.isBefore(firstTermEnd)) ||
                    (from.isBefore(firstTermStart) && to.isAfter(firstTermEnd)))
                return true;
            firstTermStart = firstTermStart.plusDays(7);
            firstTermEnd = firstTermEnd.plusDays(7);
        }
        return false;
    }

    @Override
    public boolean termIntersects(Term other) {
        return other.timeOccupied(LocalDateTime.of(startDay.plusDays(day.getValue() - startDay.getDayOfWeek().getValue()), startTime),
                LocalDateTime.of(startDay.plusDays(day.getValue() - startDay.getDayOfWeek().getValue()), endTime));
    }

    @Override
    public DayOfWeek getWeekday() {
        return day;
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyTerm that = (WeeklyTerm) o;
        return getLocation().getName().compareTo(that.getLocation().getName()) == 0 && startDay.equals(that.startDay) && endDay.equals(that.endDay) && startTime.equals(that.startTime) && endTime.equals(that.endTime) && day == that.day;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getLocation().getName() + " " + startTime.toString() + " - " + endTime.toString() + "(" + startDay.toString() + " - " + endDay.toString() + ", " + day.toString() + ")" + ": ");
        attributes.forEach((k, v) -> builder.append(k).append(":").append(v).append(" "));
        return builder.toString();
    }
}
