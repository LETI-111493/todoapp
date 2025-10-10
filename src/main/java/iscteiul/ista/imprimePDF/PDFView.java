package iscteiul.ista.imprimePDF;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import iscteiul.ista.base.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import com.vaadin.flow.server.StreamResource;

@Route(value = "pdf", layout = MainLayout.class)
@PageTitle("Gerar PDF")
public class PDFView extends VerticalLayout {

    private final PDFService pdfService;

    @Autowired
    public PDFView(PDFService pdfService) {
        this.pdfService = pdfService;

        setAlignItems(Alignment.CENTER);
        setPadding(true);
        setSpacing(true);

        TextArea input = new TextArea("Conteúdo para o PDF");
        input.setPlaceholder("Escreve aqui as linhas do teu PDF...");
        input.setWidth("400px");
        input.setHeight("150px");

        Button gerar = new Button("Gerar PDF");
        Anchor downloadLink = new Anchor();
        downloadLink.getElement().setAttribute("download", true);

        gerar.addClickListener(e -> {
            String texto = input.getValue();
            if (texto == null || texto.isBlank()) {
                input.setInvalid(true);
                input.setErrorMessage("Insere algum texto para gerar o PDF.");
                return;
            }
            input.setInvalid(false);

            List<String> linhas = Arrays.asList(texto.split("\n"));
            byte[] pdfBytes = pdfService.generateSimplePdf(linhas);

            StreamResource resource = new StreamResource("documento.pdf",
                    () -> new ByteArrayInputStream(pdfBytes));

            downloadLink.setHref(resource);
            downloadLink.setText("📥 Clica aqui para descarregar o PDF");
        });

        add(input, gerar, downloadLink);
    }
}