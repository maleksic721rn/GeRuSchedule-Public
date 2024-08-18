import rs.raf.sk.geruschedule.implementation.serialization.json.JsonScheduleReader;
import rs.raf.sk.geruschedule.implementation.serialization.json.JsonScheduleWriter;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleReader;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleWriter;

module GeRuSchedule.JsonSerialization {
    requires GeRuSchedule.Specification;
    requires com.google.gson;
    provides ScheduleWriter with JsonScheduleWriter;
    provides ScheduleReader with JsonScheduleReader;
}