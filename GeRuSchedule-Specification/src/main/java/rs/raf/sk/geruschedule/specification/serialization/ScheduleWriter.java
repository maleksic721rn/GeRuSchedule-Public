package rs.raf.sk.geruschedule.specification.serialization;

import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparison;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An interface for writing a schedule to a stream.
 *
 * @see Schedule
 */
public interface ScheduleWriter {
    /**
     * Sets the {@link TermWriter} for this instance.
     * @param termWriter The {@link TermWriter} that will be used to write schedules.
     *
     * @see TermWriter
     */
    void setTermWriter(TermWriter termWriter);

    /**
     * Writes a Schedule to a stream.
     * @param schedule The Schedule to be written.
     * @param outputStream The stream to write to.
     * @param groupByWeekday Whether to group terms by their weekday.
     * @param locName A pattern for searching location names.
     * @param startTime The beginning of the time span to write.
     * @param endTime The end of the time span to write.
     * @param locComparisons A set of comparisons to make for location attributes.
     * @param termComparisons A set of comparisons to make for term attributes.
     * @throws UnsupportedOperationException One of the search criteria cannot be applied for its corresponding attribute's type.
     * @throws IOException An IO exception occurred.
     */
    void write(Schedule schedule, OutputStream outputStream, Boolean groupByWeekday, String locName, LocalDateTime startTime, LocalDateTime endTime, List<AttributeComparison> locComparisons, List<AttributeComparison> termComparisons) throws UnsupportedOperationException, IOException;
}
