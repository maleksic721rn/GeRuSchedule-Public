package rs.raf.sk.geruschedule.implementation.serialization.json;

import com.google.gson.stream.JsonWriter;
import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparison;
import rs.raf.sk.geruschedule.specification.serialization.TermWriter;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleWriter;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.List;

public class JsonScheduleWriter implements ScheduleWriter {
    private TermWriter termWriter;
    @Override
    public void setTermWriter(TermWriter termWriter) {
        this.termWriter = termWriter;
    }

    @Override
    public void write(Schedule schedule, OutputStream outputStream, Boolean groupByWeekday, String locName, LocalDateTime startTime, LocalDateTime endTime, List<AttributeComparison> locComparisons, List<AttributeComparison> termComparisons) throws UnsupportedOperationException, IOException {
        List<Term> list = schedule.doSearch(locName, startTime, endTime, locComparisons, termComparisons);
        list.sort((term, t1) -> term.compare(t1, groupByWeekday));
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
            String[] headers = termWriter.getHeaders(schedule).toArray(new String[] {});

            writer.beginArray();

            for (Term term : list) {
                writer.beginObject();
                String[] t = termWriter.writeTerm(schedule, term).toArray(new String[] {});
                for (int i = 0; i < headers.length; i++) {
                    writer.name(headers[i]);
                    writer.value(t[i]);
                }
                writer.endObject();
            }

            writer.endArray();
        }
    }
}
