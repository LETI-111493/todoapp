package iscteiul.ista.imprimePDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFService {

    /**
     * Gera um PDF simples com uma lista de linhas de texto.
     */
    public byte[] generateSimplePdf(List<String> lines) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("📄 Relatório Gerado"));
            document.add(new Paragraph(" ")); // espaço

            for (String line : lines) {
                document.add(new Paragraph("• " + line));
            }
            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar PDF", e);

        }}}