import rs.raf.sk.geruschedule.implementation.serialization.csv.CsvScheduleReader;
import rs.raf.sk.geruschedule.implementation.serialization.csv.CsvScheduleWriter;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleReader;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleWriter;

module GeRuSchedule.CsvSerialization {
    requires GeRuSchedule.Specification;
    requires com.opencsv;
    provides ScheduleWriter with CsvScheduleWriter;
    provides ScheduleReader with CsvScheduleReader;
}