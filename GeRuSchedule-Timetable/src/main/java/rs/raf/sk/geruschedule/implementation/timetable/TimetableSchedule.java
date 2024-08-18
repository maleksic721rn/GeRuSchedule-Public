package rs.raf.sk.geruschedule.implementation.timetable;

import rs.raf.sk.geruschedule.implementation.base.AbstractSchedule;
import rs.raf.sk.geruschedule.implementation.timetable.terms.TimetableTerm;
import rs.raf.sk.geruschedule.specification.locations.Location;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimetableSchedule extends AbstractSchedule {
    @Override
    public Term addTerm(Location loc, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException {
        if(!startDay.isEqual(endDay) || day != startDay.getDayOfWeek())
            throw new UnsupportedOperationException("This term format is invalid for this type of schedule.");
        return addTerm(loc, startDay, startTime, endTime);
    }

    @Override
    public Term moveTerm(Term term, LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime, DayOfWeek day) throws IllegalArgumentException, UnsupportedOperationException {
        if(!startDay.isEqual(endDay) || day != startDay.getDayOfWeek())
            throw new UnsupportedOperationException("This term format is invalid for this type of schedule.");
        return super.moveTerm(term, startDay, endDay, startTime, endTime, day);
    }

    @Override
    public Term addTerm(Location loc, LocalDate day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, UnsupportedOperationException {
        checkTermInternal(loc, day, startTime, endTime, day.getDayOfWeek());
        TimetableTerm t = new TimetableTerm(loc, day, startTime, endTime, termManager);
        terms.add(t);
        setTermDefaults(t);
        return t;
    }
}
