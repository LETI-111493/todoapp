package iscteiul.ista.base.ui.imprimePDF;

import java.util.List;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class ImprimirPDF {
    public void exportTasksToPDF(List<Task> tasks, String filename) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA, 12);

        float yPosition = page.getMediaBox().getHeight() - 50;
        float leftMargin = 50;

        contentStream.beginText();
        contentStream.newLineAtOffset(leftMargin, yPosition);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.showText("Lista de Tarefas");
        contentStream.endText();
        yPosition -= 30;

        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (Task task : tasks) {
            String line = "Título: " + task.getTitle() + " | Descrição: " + task.getDescription();
            contentStream.beginText();
            contentStream.newLineAtOffset(leftMargin, yPosition);
            contentStream.showText(line);
            contentStream.endText();
            yPosition -= 20;
        }

        contentStream.close();
        document.save(filename);
        document.close();
    }
}