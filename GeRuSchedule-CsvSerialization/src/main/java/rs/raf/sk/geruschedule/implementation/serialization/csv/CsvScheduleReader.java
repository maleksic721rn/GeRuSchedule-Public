package rs.raf.sk.geruschedule.implementation.serialization.csv;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.serialization.TermReader;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CsvScheduleReader implements ScheduleReader {
    private TermReader termReader;
    @Override
    public void setTermReader(TermReader termReader) {
        this.termReader = termReader;
    }

    @Override
    public void read(Schedule schedule, InputStream inputStream, LocalDate startDay, LocalDate endDay, List<DayOfWeek> exemptDays) throws IOException {
        try (CSVReaderHeaderAware values = new CSVReaderHeaderAware(new InputStreamReader(inputStream))) {
            Map<String, String> row = values.readMap();
            while (row != null) {
                termReader.readTerm(schedule, row, startDay, endDay);
                row = values.readMap();
            }
        }
        catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        for (DayOfWeek d : exemptDays) {
            schedule.addExemptDay(d);
        }
    }
}
