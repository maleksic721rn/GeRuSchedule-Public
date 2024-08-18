package rs.raf.sk.geruschedule.implementation.serialization.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleReader;
import rs.raf.sk.geruschedule.specification.serialization.TermReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonScheduleReader implements ScheduleReader {
    private TermReader termReader;
    @Override
    public void setTermReader(TermReader termReader) {
        this.termReader = termReader;
    }

    private final Gson gson = new Gson();

    @Override
    public void read(Schedule schedule, InputStream inputStream, LocalDate startDay, LocalDate endDay, List<DayOfWeek> exemptDays) throws IOException {
        for (Map<String, String> row : gson.fromJson(new InputStreamReader(inputStream), new TypeToken<ArrayList<Map<String, String>>>() {}))
            termReader.readTerm(schedule, row, startDay, endDay);
        for (DayOfWeek d : exemptDays) schedule.addExemptDay(d);
    }
}
