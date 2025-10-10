package iscteiul.ista.QrCode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import iscteiul.ista.base.ui.MainLayout;

import java.util.Base64;

@Route(value = "qr", layout = MainLayout.class)
@PageTitle("QR Generator")
public class QRCodeView extends VerticalLayout {

    private final QRCodeService qrService;

    @Autowired
    public QRCodeView(QRCodeService qrService) {
        this.qrService = qrService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setPadding(true);
        setSpacing(true);

        TextField text = new TextField("Texto ou URL");
        text.setWidth("350px");
        text.setPlaceholder("Insira texto ou URL aqui...");
        text.setClearButtonVisible(true);

        Button gen = new Button("Gerar QR");
        gen.getStyle().set("margin-left", "10px");

        Image img = new Image();
        img.setAlt("QR Code");
        img.setWidth("250px");
        img.getStyle().set("border", "1px solid #ccc");
        img.getStyle().set("box-shadow", "2px 2px 8px rgba(0,0,0,0.15)");
        img.setVisible(false);  // Esconde a imagem até gerar

        // Usar FormLayout para melhor organização do input e botão
        FormLayout formLayout = new FormLayout();
        formLayout.add(text, gen);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        gen.addClickListener(e -> {
            String value = text.getValue();
            if (value == null || value.isBlank()) {
                text.setInvalid(true);
                text.setErrorMessage("Por favor insira um texto ou URL.");
                img.setVisible(false);
                return;
            }
            text.setInvalid(false);

            byte[] png = qrService.generateQRCode(value, 300);
            String base64 = Base64.getEncoder().encodeToString(png);
            String dataUrl = "data:image/png;base64," + base64;
            img.setSrc(dataUrl);
            img.setVisible(true);
        });

        add(formLayout, img);
    }
}
