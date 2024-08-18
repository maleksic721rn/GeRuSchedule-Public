package rs.raf.sk.geruschedule.implementation.serialization.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;
import rs.raf.sk.geruschedule.specification.Schedule;
import rs.raf.sk.geruschedule.specification.attributes.AttributeComparison;
import rs.raf.sk.geruschedule.specification.serialization.ScheduleWriter;
import rs.raf.sk.geruschedule.specification.serialization.TermWriter;
import rs.raf.sk.geruschedule.specification.terms.Term;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

public class PdfScheduleWriter implements ScheduleWriter {
    private TermWriter termWriter;
    @Override
    public void setTermWriter(TermWriter termWriter) {
        this.termWriter = termWriter;
    }

    @Override
    public void write(Schedule schedule, OutputStream outputStream, Boolean groupByWeekday, String locName, LocalDateTime startTime, LocalDateTime endTime, List<AttributeComparison> locComparisons, List<AttributeComparison> termComparisons) throws UnsupportedOperationException, IOException {
        List<Term> list = schedule.doSearch(locName, startTime, endTime, locComparisons, termComparisons);
        list.sort((term, t1) -> term.compare(t1, groupByWeekday));
        String[] headers = termWriter.getHeaders(schedule).toArray(new String[] {});
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        float width = 20;
        PDType0Font font;
        font = PDType0Font.load(doc, getClass().getResourceAsStream("/unifont-15.0.06.ttf"));
        Table.TableBuilder tableBuilder = Table.builder();
        {
            Row.RowBuilder rowBuilder = Row.builder().padding(10).borderWidth(1);
            for (String header : headers) {
                int size = 20 + 17 * header.length();
                width += size;
                tableBuilder.addColumnOfWidth(size);
                rowBuilder.add(TextCell.builder().font(font).text(header).build());
            }
            tableBuilder.addRow(rowBuilder.build());
        }
        for (Term term : list) {
            String[] strings = termWriter.writeTerm(schedule, term).toArray(new String[] {});
            Row.RowBuilder rowBuilder = Row.builder().padding(10).borderWidth(1);
            for (String s : strings)
                rowBuilder.add(TextCell.builder().font(font).text(s).build());
            tableBuilder.addRow(rowBuilder.build());
        }
        try (PDPageContentStream pageContent = new PDPageContentStream(doc, page)) {
            TableDrawer td = TableDrawer.builder().contentStream(pageContent).startX(10).startY(10).table(tableBuilder.build()).build();
            td.draw();
            page.setMediaBox(new PDRectangle(width, td.getFinalY() * 1.445f + 20));
        }
        doc.save(outputStream);
        doc.close();
    }
}
