import rs.raf.sk.geruschedule.implementation.timetable.TimetableSchedule;
import rs.raf.sk.geruschedule.specification.Schedule;

module GeRuSchedule.Timetable {
    requires GeRuSchedule.AbstractImplementation;
    provides Schedule with TimetableSchedule;
}