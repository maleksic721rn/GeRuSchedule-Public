package rs.raf.sk.geruschedule.specification.serialization;

import rs.raf.sk.geruschedule.specification.Schedule;
import java.time.LocalDate;
import java.util.Map;

/**
 * An interface used to read terms from table-formatted data.
 * A method for reading specialized formatted input is expected to be implemented by the application using the library.
 *
 * @see Schedule
 * @see rs.raf.sk.geruschedule.specification.terms.Term Term
 */
public interface TermReader {
    /**
     * Creates a {@link rs.raf.sk.geruschedule.specification.terms.Term Term} from a table row.
     * @param schedule The Schedule to which the term should be added.
     * @param row The table row.
     * @param startDay The day when the schedule starts.
     * @param endDay The day when the schedule ends.
     */
    void readTerm(Schedule schedule, Map<String, String> row, LocalDate startDay, LocalDate endDay);
}
