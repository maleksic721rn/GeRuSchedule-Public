import rs.raf.sk.geruschedule.implementation.weekly.WeeklySchedule;
import rs.raf.sk.geruschedule.specification.Schedule;

module GeRuSchedule.Weekly {
    requires GeRuSchedule.AbstractImplementation;
    provides Schedule with WeeklySchedule;
}