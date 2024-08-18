package rs.raf.sk.geruschedule.specification.serialization;

import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.util.List;

/**
 * An interface used to write terms as table-formatted data.
 * A method for writing specialized formatted output is expected to be implemented by the application using the library.
 *
 * @see Schedule
 * @see rs.raf.sk.geruschedule.specification.terms.Term Term
 */
public interface TermWriter {
    /**
     * Returns the headers for the schedule output.
     * @param schedule The schedule being written.
     * @return A list of fields describing the schedule.
     */
    List<String> getHeaders(Schedule schedule);

    /**
     * Returns the term information to be written.
     * @param schedule The schedule being written.
     * @param term The term being written.
     * @return A list of fields describing the given term.
     */
    List<String> writeTerm(Schedule schedule, Term term);
}
