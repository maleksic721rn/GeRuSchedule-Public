import rs.raf.sk.geruschedule.implementation.serialization.pdf.PdfScheduleWriter;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleWriter;

module GeRuSchedule.PdfSerialization {
    requires GeRuSchedule.Specification;
    requires easytable;
    requires org.apache.pdfbox;
    provides ScheduleWriter with PdfScheduleWriter;
}