package rs.raf.sk.geruschedule.specification.serialization;

import rs.raf.sk.geruschedule.specification.Schedule;

import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * An interface for reading a schedule from a stream.
 *
 * @see Schedule
 */
public interface ScheduleReader {
    /**
     * Sets the {@link TermReader} for this instance.
     * @param termReader The {@link TermReader} that will be used to read schedules.
     *
     * @see TermReader
     */
    void setTermReader(TermReader termReader);

    /**
     * Reads a {@link Schedule} from a stream.
     * @param schedule The Schedule which will be populated with the read terms.
     * @param inputStream The stream to read from.
     * @param startDay The day when the read schedule starts.
     * @param endDay The day when the read schedule ends.
     * @param exemptDays A list of exempt days.
     * @throws IOException An IO error occurred.
     */
    void read(Schedule schedule, InputStream inputStream, LocalDate startDay, LocalDate endDay, List<DayOfWeek> exemptDays) throws IOException;
}
