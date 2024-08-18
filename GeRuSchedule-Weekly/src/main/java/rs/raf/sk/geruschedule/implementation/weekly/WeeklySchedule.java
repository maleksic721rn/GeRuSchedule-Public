package rs.raf.sk.geruschedule.implementation.weekly;

import rs.raf.sk.geruschedule.implementation.base.AbstractSchedule;
import rs.raf.sk.geruschedule.implementation.weekly.terms.WeeklyTerm;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class WeeklySchedule extends AbstractSchedule {
    @Override
    public Term addTerm(Location loc, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException {
        checkTermInternal(loc, startDay, startTime, endTime, day);
        WeeklyTerm t = new WeeklyTerm(loc, startDay, endDay, startTime, endTime, day, termManager);
        terms.add(t);
        setTermDefaults(t);
        return t;
    }

    @Override
    public Term addTerm(Location loc, LocalDate day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, UnsupportedOperationException {
        return addTerm(loc, day, day, startTime, endTime, day.getDayOfWeek());
    }
}
